package sports.trademarket.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sports.trademarket.dto.AgencyJoinDto;
import sports.trademarket.dto.AgentDetailDto;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.dto.UpdateAgentDto;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.ProfileImg;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.entity.enumType.CompanyType;
import sports.trademarket.repository.AgencyRepository;
import sports.trademarket.repository.AgentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static sports.trademarket.entity.enumType.CompanyType.MID;

@ExtendWith(MockitoExtension.class)
class AgentServiceImplTest {

    @Mock
    private AgencyRepository agencyRepository;

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    @InjectMocks
    private AgentServiceImpl agentService;

    private AgentJoinDto agentJoinDto;
    private Agency agency1;
    private Agency agency2;
    private Agent agent;
    UpdateAgentDto updateDto;

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
    }

    @Test
    @DisplayName("Agent 등록")
    void registerAgent() throws Exception {
        //given
        given(agencyRepository.findById(1L)).willReturn(Optional.of(agency1));
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
        given(agentRepository.findById(1L)).willReturn(Optional.of(agent));

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
        given(agencyRepository.findById(2L)).willReturn(Optional.of(agency2));
        given(agentRepository.findById(1L)).willReturn(Optional.of(agent));

        //when
        Agent afterUpdate = agentService.updateDetils(1L, updateDto);

        //then
        assertThat(afterUpdate.getAgency().getAgencyId()).isEqualTo(updateDto.getAgencyId());
        assertThat(afterUpdate.getPhone()).isEqualTo(updateDto.getPhone());
        assertThat(afterUpdate.getCareer()).isEqualTo(updateDto.getCareer());
    }

}
