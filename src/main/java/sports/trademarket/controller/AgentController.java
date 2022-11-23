package sports.trademarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.enumType.RoleType;
import sports.trademarket.repository.AgentRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/agent")
public class AgentController {

    private final AgentRepository agentRepository;

    @PostMapping("/join")
    public void join() {

        Agent testAgent = Agent.builder()
                .agentName("Gilbert")
                .roleType(RoleType.ROLE_USER)
                .email("gilbert9172@naver.com")
                .password("$2a$04$vRKAgBifZF6x9mF54Cg10ufUbIH3/sQFXXMJtvvYuloSThR1yaM4S")
                .build();
        agentRepository.save(testAgent);
    }

}
