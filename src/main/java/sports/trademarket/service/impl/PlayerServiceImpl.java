package sports.trademarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.PlayerRegisterDto;
import sports.trademarket.entity.*;
import sports.trademarket.entity.embaddedType.ContractCondition;
import sports.trademarket.entity.embaddedType.SoccerPlayerStats;
import sports.trademarket.exceptions.spring.DuplicationException;
import sports.trademarket.exceptions.spring.NoSuchDataException;
import sports.trademarket.repository.*;
import sports.trademarket.service.PlayerService;

import static sports.trademarket.exceptions.spring.ErrorConstants.duplicatedPlayer;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final AgentRepository agentRepository;
    private final PlayerRepository playerRepository;
    private final SoccerRepository soccerRepository;
    private final TeamRepository teamRepository;
    private final PositionRepository positionRepository;

    @Value("${img.path.player}")
    private String savePath;

    @Override
    public void registerPlayer(PlayerRegisterDto registerDto, MultipartFile playerImg) {

        checkDuplicatedPlayer(registerDto);

        Soccer player = Soccer.builder()
                .agent(myAgent(registerDto))
                .team(myTeam(registerDto))
                .position(myPosition(registerDto))
                .name(registerDto.getName())
                .age(registerDto.getAge())
                .fifaRegNum(registerDto.getFifaRegNum())
                .preferFoot(registerDto.getPreferFoot())
                .skillLevel(registerDto.getSkillLevel())
                .playerStats(myStat(registerDto))
                .contractCondition(myContractCond(registerDto))
                .build();

        player.saveImgFile(playerImg, savePath);
        playerRepository.save(player);
    }

    private void checkDuplicatedPlayer(PlayerRegisterDto registerDto) {
        soccerRepository.findByFifaRegNum(registerDto.getFifaRegNum())
                .ifPresent(player -> {
                    throw new DuplicationException(duplicatedPlayer);
                });
    }

    private ContractCondition myContractCond(PlayerRegisterDto registerDto) {
        return ContractCondition.allContractCond(
                registerDto.getTransferFee(), registerDto.getCurrentPayment(), registerDto.getExpectedPayment(),
                registerDto.getUnit(), registerDto.getDivision(), registerDto.getContractExpiryDt()
        );
    }

    private SoccerPlayerStats myStat(PlayerRegisterDto registerDto) {
        return SoccerPlayerStats.allStats(registerDto.getGoal(), registerDto.getAssist(),
                registerDto.getClear(), registerDto.getCleanSheet());
    }

    private Position myPosition(PlayerRegisterDto registerDto) {
        return positionRepository.findById(registerDto.getPositionId())
                .orElseThrow(NoSuchDataException::new);
    }

    private Team myTeam(PlayerRegisterDto registerDto) {
        return teamRepository.findById(registerDto.getTeamId())
                .orElseThrow(NoSuchDataException::new);
    }

    private Agent myAgent(PlayerRegisterDto registerDto) {
        return agentRepository.findById(registerDto.getAgentId())
                .orElseThrow(NoSuchDataException::new);
    }
    
}
