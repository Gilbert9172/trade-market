package sports.trademarket.service.impl;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.entity.enumType.CompanyType;
import sports.trademarket.repository.AgencyRepository;
import sports.trademarket.repository.AgentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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

    @Test
    @DisplayName("Agent 등록")
    void registerAgent() throws Exception {

        Agency agency = testAgencyData();
        Agent agent = testAgentData();
        AgentJoinDto agentJoinDto = testAgentDto();

        //given
        given(agencyRepository.findById(1L)).willReturn(Optional.of(agency));
        given(agentRepository.save(any())).willReturn(agent);
        given(passwordEncoder.encode(anyString())).willReturn("hashedPassword");

        //when
        agentService.register(agentJoinDto, null);

        //then
        assertThat(agent.getPassword()).isEqualTo("hashedPassword");

        //verify
        verify(agentService).register(agentJoinDto, null);

    }

    @NotNull
    private AgentJoinDto testAgentDto() {
        return new AgentJoinDto(
                1L, "gilbert", "gilbert@naver.com",
                "1234","01012344321", 3
        );
    }

    private Agency testAgencyData() {
        Address address = new Address("seoul", "nowon", "lotteAPT");
        return Agency.builder()
                .agencyName("참좋은 에이전씨")
                .ceoName("gilbert")
                .companyType(CompanyType.MID)
                .address(address)
                .homepageUrl("test@naver.com")
                .career(10).build();
    }

    private Agent testAgentData() {
        return Agent.builder()
                .agentName("gilbert")
                .email("gilbert@naver.com")
                .phone("01012344321")
                .career(3)
                .password("hashedPassword")
                .build();
    }

}