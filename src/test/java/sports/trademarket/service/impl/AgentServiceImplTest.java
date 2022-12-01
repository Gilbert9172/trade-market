package sports.trademarket.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sports.trademarket.dto.AgentDetailDto;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.dto.UpdateAgentDto;
import sports.trademarket.entity.*;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.entity.enumType.CompanyType;
import sports.trademarket.exceptions.spring.BeforeReOfferTermException;
import sports.trademarket.repository.AgencyRepository;
import sports.trademarket.repository.AgentRepository;
import sports.trademarket.repository.OfferRepository;
import sports.trademarket.repository.PlayerRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static sports.trademarket.entity.enumType.CompanyType.MID;
import static sports.trademarket.entity.enumType.ContractDivision.*;
import static sports.trademarket.entity.enumType.ContractStatus.*;
import static sports.trademarket.entity.enumType.CurrencyUnit.*;

@ExtendWith(MockitoExtension.class)
class AgentServiceImplTest {

    @Mock
    private AgencyRepository agencyRepository;

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    @InjectMocks
    private AgentServiceImpl agentService;

    private AgentJoinDto agentJoinDto;
    private Agency agency1;
    private Agency agency2;
    private Agent agent;
    private UpdateAgentDto updateDto;
    private Player player;

    @Captor
    ArgumentCaptor<Contract> offerContract;

    @BeforeEach
    void setTestData() {

        Address address = Address.of("seoul", "nowon", "lotteAPT");

        ProfileImg profileImg = new ProfileImg(1L, "orgName", ".png",
                "savedPath", "savedFileName");

        updateDto = new UpdateAgentDto(2L, "01099998888",20);

        agentJoinDto = new AgentJoinDto(
                1L, "gilbert", "gilbert@naver.com",
                "1234567", "01012344321", 3
        );

        agency1 = Agency.builder()
                .agencyId(1L)
                .agencyName("참좋은 에이전씨")
                .ceoName("gilbert")
                .companyType(MID)
                .address(address)
                .homepageUrl("test@naver.com")
                .career(10)
                .active(1).build();

        agency2 = Agency.builder()
                .agencyId(2L)
                .agencyName("참좋은 에이전씨2")
                .ceoName("gilbert2")
                .companyType(CompanyType.BIG)
                .address(address)
                .homepageUrl("test2@naver.com")
                .career(20)
                .active(1).build();

        agent = Agent.builder()
                .agentId(1L)
                .agentName("gilbert")
                .email("gilbert@naver.com")
                .phone("01012344321")
                .profileImg(profileImg)
                .agency(agency1)
                .career(3)
                .password("hashedPassword")
                .active(1)
                .build();

        Team team = new Team(1L, "TeamA",
                5, "London", "ownerA",
                "coach-A", "coachA@naver.com", 1);

        Position position = new Position(2L, "SS",
                new Position(1L, "FW", null));

        player = new Player(1L, agent, team, position, "메시", 34);
    }

    @Test
    @DisplayName("Agent 등록")
    void registerAgent() throws Exception {
        //given
        given(agencyRepository.findById(1L)).willReturn(of(agency1));
        given(agentRepository.save(any())).willReturn(agent);
        given(passwordEncoder.encode(anyString())).willReturn("hashedPassword");

        //when
        agentService.register(agentJoinDto, null);

        //then
        assertThat(agent.getPassword()).isEqualTo("hashedPassword");

        //verify
        verify(agentService).register(agentJoinDto, null);

    }

    @Test
    @DisplayName("Agent 상세조회")
    void findAgentByID() throws Exception {
        //given
        given(agentRepository.findById(1L)).willReturn(of(agent));

        //when
        Agent findAgent = agentService.findAgentById(1L);
        AgentDetailDto agentDetailDto = AgentDetailDto.toDto(findAgent);

        //then
        assertThat(agentDetailDto.getAgentName()).isEqualTo(agent.getAgentName());
        assertThat(agentDetailDto.getImgPath()).isEqualTo("savedPath/savedFileName");

        //verify
        verify(agentService).findAgentById(1L);
    }

    @Test
    @DisplayName("Agent 상세 수정")
    void updateAgentDetails() throws Exception {
        //given
        given(agencyRepository.findById(2L)).willReturn(of(agency2));
        given(agentRepository.findById(1L)).willReturn(of(agent));

        //when
        Agent afterUpdate = agentService.updateDetils(1L, updateDto);

        //then
        assertThat(afterUpdate.getAgency().getAgencyId()).isEqualTo(updateDto.getAgencyId());
        assertThat(afterUpdate.getPhone()).isEqualTo(updateDto.getPhone());
        assertThat(afterUpdate.getCareer()).isEqualTo(updateDto.getCareer());
    }

    @Test
    @DisplayName("최초 이적 제안하기")
    void offerTransfer() throws Exception {
        Contract contractCond = Contract.builder()
                .contractId(1L).payment(1000L).transferFee(2000L)
                .contractDivision(LOAN).contractYear(1).contractMonth(6)
                .currencyUnit(EURO).build();

        Offer offer = Offer.builder()
                .offerId(1L).agent(agent).player(player)
                .contract(contractCond).contractStatus(NEGOTIATING)
                .build();

        Long playerId = 1L;

        //given
        given(agentRepository.findById(any())).willReturn(ofNullable(agent));
        given(playerRepository.findById(any())).willReturn(ofNullable(player));
        given(offerRepository.findPreviousOffer(anyLong(), anyLong())).willReturn(empty());
        given(offerRepository.save(any())).willReturn(offer);

        //when
        Offer offerTransfer = agentService.offerTransfer(playerId, contractCond);

        //then
        assertThat(offer.getPlayer().getName()).isEqualTo(offerTransfer.getPlayer().getName());
    }

    @Test
    @DisplayName("이미 제안을 한 경우 - 기존 제안 수정")
    void alreadyOfferd() throws Exception {
        Contract contractCond = Contract.builder()
                .contractId(1L).payment(1000L).transferFee(2000L)
                .contractDivision(LOAN).contractYear(1).contractMonth(6)
                .currencyUnit(EURO).build();

        Contract newCond = Contract.builder()
                .contractId(2L).payment(2000L).transferFee(4000L)
                .contractDivision(LOAN).contractYear(3).contractMonth(6)
                .currencyUnit(EURO).build();

        Offer firstOffer = Offer.builder()
                .offerId(1L).agent(agent).player(player)
                .contract(contractCond).contractStatus(NEGOTIATING)
                .build();

        Offer secOffer = Offer.builder()
                .offerId(1L).agent(agent).player(player)
                .contract(contractCond).contractStatus(NEGOTIATING)
                .build();

        LocalDateTime firstOfferDt =
                LocalDateTime.of(2022, 11, 28,
                        2, 13, 10, 697);

        Long playerId = 1L;


        //given
        Offer mockedOffer = mock(Offer.class);
        given(offerRepository.findPreviousOffer(anyLong(), anyLong())).willReturn(Optional.ofNullable(firstOffer));

        //when
        agentService.offerTransfer(playerId, contractCond);

        //then
//        assertThat(offer.getOfferId()).isEqualTo(offer.getOfferId());
//        assertThat(offer.getContract().getContractId()).isEqualTo(newCond.getContractId());
//        assertThat(offer.getContract().getPayment()).isEqualTo(newCond.getPayment());
//        assertThat(offer.getContract().getTransferFee()).isEqualTo(newCond.getTransferFee());
//        assertThat(offer.getContract().getContractYear()).isEqualTo(newCond.getContractYear());
        verify(mockedOffer).updateOffer(offerContract.capture());

    }

    @Test
    @DisplayName("이전 제안 이후 3일이 지나지 않았을 경우")
    void before3days() throws Exception {
        //given
        LocalDateTime firstOffer =
                LocalDateTime.of(2022, 11, 28,
                        2, 13, 10, 697);

        LocalDateTime secOffer =
                LocalDateTime.of(2022, 11, 29,
                        18, 42, 10, 321);

        //when
        int elapsedDays =
                (int) Math.round((ChronoUnit.SECONDS.between(firstOffer, secOffer)/ 86400.0) * 100) / 100;

        assertThatThrownBy(() -> {
            if (elapsedDays < 3) {
                throw new BeforeReOfferTermException(
                        "이전 제안 후 3일 후에 다시 제안할 수 있습니다. => " + elapsedDays);
            }
        }).isInstanceOf(BeforeReOfferTermException.class);

    }

    @Test
    @DisplayName("이전 제안 이후 3일이 지났을 경우")
    void after3days() throws Exception {
        //given
        LocalDateTime firstOffer =
                LocalDateTime.of(2022, 11, 28,
                        2, 13, 10, 697);

        LocalDateTime secOffer =
                LocalDateTime.of(2022, 12, 02,
                        18, 42, 10, 321);

        //when
        int elapsedDays =
                (int) Math.round((ChronoUnit.SECONDS.between(firstOffer, secOffer)/ 86400.0) * 100) / 100;

        //then
        assertThat(elapsedDays).isGreaterThanOrEqualTo(3);
    }


}
