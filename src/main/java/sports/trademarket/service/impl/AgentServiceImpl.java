package sports.trademarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.dto.UpdateAgentDto;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.Agent;
import sports.trademarket.exceptions.spring.DuplicationException;
import sports.trademarket.exceptions.spring.NoSuchDataException;
import sports.trademarket.repository.AgencyRepository;
import sports.trademarket.repository.AgentRepository;
import sports.trademarket.service.AgentService;

import static sports.trademarket.exceptions.spring.ErrorConstants.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgencyRepository agencyRepository;
    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${img.path.agent}")
    private String savePath;

    @Override
    @Transactional
    public void register(AgentJoinDto agentJoin, MultipartFile file) {

        checkDuplicatedAgent(agentJoin);
        encodingPassword(agentJoin);
        Agent agent = Agent.toEntity(agentJoin);
        agent.belongingAgency(findAgencyById(agentJoin.getAgencyId()));
        agent.saveImgIfExist(file, savePath);
        agentRepository.save(agent);
    }

    @Override
    public Agent findAgentById(Long agentID) {
        return agentRepository.findById(agentID)
                .orElseThrow(() -> new NoSuchDataException(noSuchDataExist));
    }

    @Override
    @Transactional
    public Agent updateDetils(Long agentId, UpdateAgentDto updateDto) {

        Agent agent = this.findAgentById(agentId);
        Agency agency = findAgencyById(updateDto.getAgencyId());
        agent.updateDetails(updateDto,agency);
        return agent;
    }

    private Agency findAgencyById(Long agencyId) {
        return agencyRepository.findById(agencyId)
                .orElseThrow(NoSuchDataException::new);
    }

    private void checkDuplicatedAgent(AgentJoinDto agentJoin) {
        agentRepository.findByEmail(agentJoin.getEmail())
                        .ifPresent(agent -> {
                            throw new DuplicationException(duplicatedAgent);
                        });
    }

    private void encodingPassword(AgentJoinDto agentJoin) {
        agentJoin.setPassword(passwordEncoder.encode(agentJoin.getPassword()));
    }

}
