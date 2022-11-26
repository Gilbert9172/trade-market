package sports.trademarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.ProfileImg;
import sports.trademarket.exceptions.spring.DuplicationException;
import sports.trademarket.exceptions.spring.IllegalFileNameException;
import sports.trademarket.exceptions.spring.NoSuchDataException;
import sports.trademarket.exceptions.spring.SaveFileException;
import sports.trademarket.repository.AgencyRepository;
import sports.trademarket.repository.AgentRepository;
import sports.trademarket.service.AgentService;

import java.io.IOException;
import java.util.Optional;

import static sports.trademarket.exceptions.spring.ErrorConstants.*;
import static sports.trademarket.utililty.FileUtil.*;
import static sports.trademarket.utililty.FileUtil.extractExt;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgencyRepository agencyRepository;
    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${profile.img.path}")
    private String imgSavePath;

    @Override
    @Transactional
    public void register(AgentJoinDto agentJoin, MultipartFile file) {

        checkDuplicatedAgent(agentJoin);
        encodingPassword(agentJoin);
        Agent agent = Agent.toEntity(agentJoin);
        belongingAgency(agentJoin.getAgencyId(), agent);
        saveFileIfExist(file, agent);
        agentRepository.save(agent);
    }

    @Override
    public Agent findAgentById(Long agentID) {
        return agentRepository.findById(agentID)
                .orElseThrow(() -> new NoSuchDataException(noSuchDataExist));
    }

    private void checkDuplicatedAgent(AgentJoinDto agentJoin) {
        agentRepository.findByEmail(agentJoin.getEmail())
                        .ifPresent(agent -> {
                            throw new DuplicationException(duplicatedAgent);
                        });
    }

    private void belongingAgency(Long agencyId, Agent agent) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(NoSuchDataException::new);
        agent.setAgency(agency);
    }

    private void saveFileIfExist(MultipartFile file, Agent agent) {
        if (file != null) {
            agent.setProfileImg(saveProfileImg(file));
        }
    }

    private void encodingPassword(AgentJoinDto agentJoin) {
        agentJoin.setPassword(passwordEncoder.encode(agentJoin.getPassword()));
    }

    private ProfileImg saveProfileImg(MultipartFile file) {

        String orgFileName = Optional.ofNullable(file.getOriginalFilename())
                .orElseThrow(() -> new IllegalFileNameException(illegalFileName));

        try {
            ProfileImg img = ProfileImg.builder()
                    .originalFileNm(orgFileName)
                    .originalFileExt(extractExt(orgFileName))
                    .savedFileNm(generateServerFileNm(orgFileName))
                    .savedFilePath(imgSavePath)
                    .build();

            save(imgSavePath, orgFileName, file);
            return img;

        } catch (IOException e) {
            throw new SaveFileException(fileSaveFail);
        }
    }
}
