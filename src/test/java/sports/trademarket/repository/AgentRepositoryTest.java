package sports.trademarket.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.Agent;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AgentRepositoryTest {

    @Autowired AgencyRepository agencyRepository;

    @Autowired AgentRepository agentRepository;

    @Test
    @DisplayName("Agent save")
    void saveAgent() throws Exception {
        //given
        Agency agency = agencyRepository.findById(1L)
                .orElseThrow(NullPointerException::new);

        Agent agent = Agent.builder()
                .agentName("Gilbert")
                .email("gilbert950904@gmail.com")
                .agency(agency)
                .password("$2a$04$vRKAgBifZF6x9mF54Cg10ufUbIH3/sQFXXMJtvvYuloSThR1yaM4S")
                .phone("01012345678")
                .build();

        //when
        Agent testAgent = agentRepository.save(agent);

        //then
        assertThat(testAgent.getEmail()).isEqualTo("gilbert950904@gmail.com");
    }

}