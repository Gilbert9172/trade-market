package sports.trademarket.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import sports.trademarket.dto.PlayerRegisterDto;
import sports.trademarket.entity.*;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.exceptions.spring.DuplicationException;
import sports.trademarket.repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static sports.trademarket.entity.enumType.CompanyType.*;
import static sports.trademarket.entity.enumType.ContractDivision.*;
import static sports.trademarket.entity.enumType.CurrencyUnit.*;
import static sports.trademarket.entity.enumType.SkillLevel.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    AgentRepository agentRepository;

    @Mock
    PlayerRepository playerRepository;

    @Mock
    SoccerRepository soccerRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    PositionRepository positionRepository;

    @Spy
    @InjectMocks
    SoccerServiceImpl playerService;

    private Agent agent;
    private Team team;
    private Position position;
    private PlayerRegisterDto regDto;

    @BeforeEach
    public void setUp() throws IOException {
        agent = testAgent();
        team = testTeam();
        position = testPosition();
        regDto = testRegDto();
    }

    @Test
    @DisplayName("player 등록")
    void registerPlayer() throws Exception {

        MockMultipartFile file = testImgFile();
        ReflectionTestUtils.setField(playerService, "savePath", "/Users/giljun/trade-market/player");

        //given
        given(agentRepository.findById(1L)).willReturn(Optional.of(agent));
        given(teamRepository.findById(1L)).willReturn(Optional.of(team));
        given(positionRepository.findById(1L)).willReturn(Optional.of(position));

        //when
        playerService.registerPlayer(regDto, file);

        //verify
        then(playerRepository).should().save(any());
    }

    @Test
    @DisplayName("player 중복 O")
    void duplicatedPlayer() throws Exception {

        //given
        PlayerRegisterDto regDto = testRegDto();
        MockMultipartFile file = testImgFile();

        given(soccerRepository.findByFifaRegNum(anyString()))
                .willThrow(DuplicationException.class);

        //willThrow(new DuplicationException()).given(soccerRepository).findByFifaRegNum(anyString());

        // when, then
        assertThatThrownBy(() -> playerService.registerPlayer(regDto, file))
                .isInstanceOf(DuplicationException.class);

    }

    @Test
    @DisplayName("player 중복 X")
    void NotDuplicatedPlayer() throws Exception {

        //given
        PlayerRegisterDto regDto = testRegDto();
        MockMultipartFile file = testImgFile();
        ReflectionTestUtils.setField(playerService, "savePath", "/Users/giljun/trade-market/player");

        given(agentRepository.findById(1L)).willReturn(Optional.of(agent));
        given(teamRepository.findById(1L)).willReturn(Optional.of(team));
        given(positionRepository.findById(1L)).willReturn(Optional.of(position));
        given(soccerRepository.findByFifaRegNum(anyString())).willReturn(Optional.empty());

        // when
        playerService.registerPlayer(regDto, file);

        // then
        then(soccerRepository).should().findByFifaRegNum(anyString());
    }

    private Agency testAgency() {
        Address address = Address.of("seoul", "nowon", "lotteAPT");
        return Agency.builder()
                .agencyId(1L).agencyName("참좋은 에이전씨")
                .ceoName("gilbert").companyType(MID)
                .address(address).homepageUrl("test@naver.com")
                .career(10).active(1).build();
    }

    private Agent testAgent() {
        ProfileImg profileImg = new ProfileImg(1L, "orgName", ".png",
                "savedPath", "savedFileName");
        return Agent.builder()
                .agentId(1L).agentName("gilbert")
                .email("gilbert@naver.com")
                .phone("01012344321")
                .profileImg(profileImg)
                .agency(testAgency())
                .career(3).password("hashedPassword")
                .active(1).build();
    }

    private Team testTeam() {
        return new Team(1L, "TeamA",
                5, "London", "ownerA",
                "coach-A", "coachA@naver.com", 1);
    }

    private Position testPosition() {
        Position main = new Position(1L, "FW", null);
        return new Position(2L, "SS", main);
    }

    private PlayerRegisterDto testRegDto() {
        return new PlayerRegisterDto(
                1L, 1L, "20118415",
                "조규성", 28, "B",
                ADVANCED, 28, 12, 1,
                0, 1L, 17000000L,
                300000L, 500000L,
                EURO, PERMANENT, LocalDate.of(2025,5,19)
        );
    }

    private MockMultipartFile testImgFile() throws IOException {

        Path path = Paths.get("src/test/resources/test.png");
        String name = "testImg.png";
        String originalFileName = "testImg.png";
        String contentType = "img/png";
        byte[] content = Files.readAllBytes(path);
        return  new MockMultipartFile(name, originalFileName, contentType, content);

    }
}