package sports.trademarket.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sports.trademarket.entity.*;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.entity.embaddedType.ContractCondition;
import sports.trademarket.entity.embaddedType.SoccerPlayerStats;
import sports.trademarket.entity.enumType.CompanyType;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;
import static sports.trademarket.entity.enumType.ContractDivision.PERMANENT;
import static sports.trademarket.entity.enumType.CurrencyUnit.EURO;
import static sports.trademarket.entity.enumType.SkillLevel.ADVANCED;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class SoccerRepositoryTest {

    @Autowired SoccerRepository soccerRepository;
    @Autowired PlayerRepository playerRepository;

    private Soccer testPlayer;

    @BeforeEach
    void setTestData() {
        ProfileImg profileImg = new ProfileImg(1L, "orgName", ".png",
                "savedPath", "savedFileName");

        Team team = new Team(1L, "TeamA",
                5, "London", "ownerA",
                "coach-A", "coachA@naver.com", 1);

        Position position = new Position(2L, "SS",
                new Position(1L, "FW", null));

        Address address = Address.of("seoul", "nowon", "lotteAPT");

        ContractCondition cond = ContractCondition.allContractCond(
                10000L, 100L, 200L,
                EURO, PERMANENT, LocalDate.of(2025, 10, 10)
        );

        SoccerPlayerStats stats = SoccerPlayerStats.allStats(10, 10, 0, 0);

        Agency agency = Agency.builder()
                .agencyId(1L)
                .agencyName("참좋은 에이전씨")
                .ceoName("gilbert")
                .companyType(CompanyType.MID)
                .address(address)
                .homepageUrl("test@naver.com")
                .career(10)
                .active(1).build();

        Agent agent = Agent.builder()
                .agentId(1L)
                .agentName("gilbert")
                .email("gilbert@naver.com")
                .phone("01012344321")
                .profileImg(profileImg)
                .agency(agency)
                .career(3)
                .password("hashedPassword")
                .active(1).build();

        testPlayer = Soccer.builder()
                .agent(agent)
                .team(team)
                .position(position)
                .name("정길준")
                .age(28)
                .fifaRegNum("1234567")
                .preferFoot("R")
                .skillLevel(ADVANCED)
                .playerStats(stats)
                .contractCondition(cond)
                .build();
    }
    
    @Test
    @DisplayName("Player 등록")
    void registerPlayer() throws Exception {
        //when
        Soccer player = playerRepository.save(testPlayer);

        //then
        assertThat(player.getFifaRegNum()).isEqualTo(testPlayer.getFifaRegNum());
    }

    @Test
    @DisplayName("player 중복O")
    void duplicatedPlayer() throws Exception {

        // given
        playerRepository.save(testPlayer);

        // when
        Optional<Soccer> byFifaRegNum = soccerRepository.findByFifaRegNum("1234567");

        // then
        assertThat(byFifaRegNum).isNotEmpty();
    }

    @Test
    @DisplayName("player 중복X")
    void notDuplicatedPlayer() throws Exception {
        //given
        String fifaNum = "123456";

        // when
        Optional<Soccer> byFifaRegNum = soccerRepository.findByFifaRegNum(fifaNum);

        // then
        assertThat(byFifaRegNum).isEmpty();
    }

}