package sports.trademarket.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sports.trademarket.entity.*;
import sports.trademarket.entity.embaddedType.Address;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;
import static sports.trademarket.entity.enumType.CompanyType.MID;
import static sports.trademarket.entity.enumType.ContractDivision.LOAN;
import static sports.trademarket.entity.enumType.ContractStatus.NEGOTIATING;
import static sports.trademarket.entity.enumType.CurrencyUnit.EURO;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class OfferRepositoryTest {

    @Autowired OfferRepository offerRepository;
    @Autowired AgencyRepository agencyRepository;
    @Autowired AgentRepository agentRepository;

    private Agency agency;
    private Agent agent;
    private Player player;

    @BeforeEach
    void setData() {
        Team team = new Team(1L, "TeamA",
                5, "London", "ownerA",
                "coach-A", "coachA@naver.com", 1);

        Position position = new Position(2L, "SS",
                new Position(1L, "FW", null));

        Address address = Address.of("seoul", "nowon", "lotteAPT");

        ProfileImg profileImg = new ProfileImg(1L, "orgName", ".png",
                "savedPath", "savedFileName");

        player = new Player(1L, agent, team, position, "메시", 34);

        agency = Agency.builder()
                .agencyId(1L)
                .agencyName("참좋은 에이전씨")
                .ceoName("gilbert")
                .companyType(MID)
                .address(address)
                .homepageUrl("test@naver.com")
                .career(10)
                .active(1).build();

        agent = Agent.builder()
                .agentId(1L)
                .agentName("gilbert")
                .email("gilbert@naver.com")
                .phone("01012344321")
                .profileImg(profileImg)
                .agency(agency)
                .career(3)
                .password("hashedPassword")
                .active(1)
                .build();

    }

    @Test
    @Disabled
    @DisplayName("이적 제안 찾기")
    void findPreviousOffer() throws Exception {
        //given
        Contract contractCond = Contract.builder()
                .contractId(1L).payment(1000L).transferFee(2000L)
                .contractDivision(LOAN).contractYear(1).contractMonth(6)
                .currencyUnit(EURO).build();


        Offer offer = Offer.builder()
                .offerId(1L).agent(agent).player(player)
                .contract(contractCond).contractStatus(NEGOTIATING)
                .build();

        Agency testAgency = agencyRepository.save(agency);
        Agent testAgent = agentRepository.save(agent);
        Offer saveOffer = offerRepository.save(offer);

        //when
        Optional<Offer> previousOffer = offerRepository.findPreviousOffer(agent.getAgentId(), player.getPlayerId());

        //then
         assertThat(previousOffer).isNotEmpty();
    }

}