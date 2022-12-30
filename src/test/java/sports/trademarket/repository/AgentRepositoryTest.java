package sports.trademarket.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.entity.enumType.CompanyType;

import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;
import static org.assertj.core.api.Assertions.*;

@Disabled
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AgentRepositoryTest {

    @Autowired AgencyRepository agencyRepository;

    @Autowired AgentRepository agentRepository;

    private Agent testAgent;

    @BeforeEach
    void setTestData() {
        Address address = Address.of("Seoul", "nowon", "lotter");
        Agency testAgency = Agency.builder()
                .ceoName("Gilbert")
                .companyType(CompanyType.MID)
                .address(address)
                .homepageUrl("test@gmail.com")
                .career(10)
                .active(1)
                .build();

        agencyRepository.save(testAgency);

        testAgent = Agent.builder()
                .agentName("Gilbert")
                .email("gilbert950904@gmail.com")
                .agency(testAgency)
                .password("$2a$04$vRKAgBifZF6x9mF54Cg10ufUbIH3/sQFXXMJtvvYuloSThR1yaM4S")
                .phone("01012345678")
                .active(1)
                .build();
    }

    @Test
    @DisplayName("Agent 저장")
    void saveAgent() throws Exception {
        //given
        Agent agent = testAgent;

        //when
        Agent testAgent = agentRepository.save(agent);

        //then
        assertThat(testAgent.getEmail()).isEqualTo("gilbert950904@gmail.com");
    }

    @Test
    @DisplayName("Agent 상세조회")
    void findAgent() throws Exception {
        //given
        Agent agent = agentRepository.save(testAgent);

        //when
        Agent findAgent = agentRepository.findById(agent.getAgentId())
                .orElseThrow(NullPointerException::new);;

        //then
        assertThat(testAgent.getAgentName()).isEqualTo(findAgent.getAgentName());
    }

    @Test
    @DisplayName("Agent 중복O")
    void duplicatedAgent() throws Exception {
        //given
        Agent agent = agentRepository.save(testAgent);

        //when
        Optional<Agent> findAgent = agentRepository.findByEmail(agent.getEmail());

        // then
        assertThat(findAgent).isNotEmpty();

    }

    @Test
    @DisplayName("Agent 중복X")
    void notDuplicatedAgent() throws Exception {
        //given
        String email = "test123@gmail.com";

        //when
        Optional<Agent> findAgent = agentRepository.findByEmail(email);

        //then
        assertThat(findAgent).isEmpty();
    }

}