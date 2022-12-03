package sports.trademarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.dto.UpdateAgentDto;
import sports.trademarket.entity.*;
import sports.trademarket.exceptions.spring.BeforeReOfferTermException;
import sports.trademarket.exceptions.spring.DuplicationException;
import sports.trademarket.exceptions.spring.NoSuchDataException;
import sports.trademarket.repository.*;
import sports.trademarket.service.AgentService;

import java.util.Optional;

import static java.time.LocalDateTime.*;
import static sports.trademarket.exceptions.spring.ErrorConstants.*;
import static sports.trademarket.utililty.JwtUtil.agentId;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgencyRepository agencyRepository;
    private final AgentRepository agentRepository;
    private final PlayerRepository playerRepository;
    private final OfferRepository offerRepository;
    private final ContractRepository contractRepository;
    private final PasswordEncoder passwordEncoder;
    private final TermChecker termChecker;

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

    @Override
    @Transactional
    public Offer offerTransfer(Long playerId ,Contract contract) throws BeforeReOfferTermException {

        Long offerAgentId = agentId();

        Optional<Offer> optionalOffer = offerRepository.findPreviousOffer(offerAgentId, playerId);
        if (optionalOffer.isEmpty()) {
            Agent offerAgent = findAgentById(offerAgentId);
            Player player = findPlayerById(playerId);
            Offer offer = Offer.createOffer(offerAgent, player, contract);
            return offerRepository.save(offer);
        } else {
            Offer previousOffer = optionalOffer.get();
            Long prevcontractId = previousOffer.getContract().getContractId();
            int term = termChecker.getTerm(previousOffer.getModifiedDt(), now());
            if (moreThenThreeDays(term)) {
                previousOffer.updateOffer(contract);
                contractRepository.deleteById(prevcontractId);
                return previousOffer;
            } else {
                throw new BeforeReOfferTermException(beforeThreeDays + term);
            }
        }
    }

    private Player findPlayerById(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(NoSuchDataException::new);
    }

    public boolean moreThenThreeDays(int elapsedDays) {
        return elapsedDays >= 3;
    }

    private Agency findAgencyById(Long agencyId) {
        return agencyRepository.findById(agencyId)
                .orElseThrow(NoSuchDataException::new);
    }

    private void checkDuplicatedAgent(AgentJoinDto agentJoin) {
        agentRepository.findByEmailOrPhone(agentJoin.getEmail(), agentJoin.getPhone())
                        .ifPresent(agent -> {
                            throw new DuplicationException(duplicatedAgent);
                        });
    }

    private void encodingPassword(AgentJoinDto agentJoin) {
        agentJoin.setPassword(passwordEncoder.encode(agentJoin.getPassword()));
    }

}
