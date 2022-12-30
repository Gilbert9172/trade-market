package sports.trademarket.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.Player;
import sports.trademarket.entity.Soccer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.*;

@Disabled
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlayerRepositoryTest {

    @Autowired PlayerRepository playerRepository;
    @Autowired AgentRepository agentRepository;

    private Agent agent;

    @BeforeEach
    void setData() {
        //given
        agent = Agent.builder()
                .agentName("gilbert")
                .email("gilbert@naver.com")
                .phone("01012344321")
                .career(3)
                .password("hashedPassword")
                .active(1)
                .build();
        agentRepository.save(agent);

    }

    @Test
    @DisplayName("같은 에이전트에게 의뢰한 선수목록")
    void playersBelongingToSameAgent() throws Exception {

        List<Player> players = new ArrayList<>();
        for (long i = 0; i < 10L; i++) {
            Soccer soccer = Soccer.builder().agent(agent).build();
            playerRepository.save(soccer);
            players.add(soccer);
        }
        PageRequest pageRequest =
                PageRequest.of(0, 4, Sort.by(DESC, "name"));
        //when
        Page<Player> result = playerRepository.findClientByAgentId(agent.getAgentId(), pageRequest);

        //then
        assertThat(players.size()).isEqualTo(result.getTotalElements());
        assertThat(result.getTotalPages()).isEqualTo(3);
    }

}