package sports.trademarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.dto.ContractEmailDto;
import sports.trademarket.dto.UpdateAgentDto;
import sports.trademarket.entity.*;
import sports.trademarket.exceptions.spring.BeforeReOfferTermException;
import sports.trademarket.exceptions.spring.DuplicationException;
import sports.trademarket.exceptions.spring.NoSuchDataException;
import sports.trademarket.repository.*;
import sports.trademarket.service.AgentService;
import sports.trademarket.service.MailService;

import java.util.Optional;

import static java.time.LocalDateTime.*;
import static sports.trademarket.dto.ContractEmailDto.*;
import static sports.trademarket.exceptions.spring.ErrorConstants.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgencyRepository agencyRepository;
    private final AgentRepository agentRepository;
    private final PlayerRepository playerRepository;
    private final OfferRepository offerRepository;
    private final ContractRepository contractRepository;
    private final MailService mailService;
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
    public Offer offerTransfer(Long agnetId, Long playerId ,Contract contract) throws BeforeReOfferTermException {

        Agent offerAgent = findAgentById(agnetId);
        Player player = findPlayerById(playerId);
        Offer offer;
        Optional<Offer> optionalOffer = offerRepository.findPreviousOffer(agnetId, playerId);

        if (optionalOffer.isEmpty()) {
            offer = Offer.createOffer(offerAgent, player, contract);
            offerRepository.save(offer);
        } else {
            offer = optionalOffer.get();
            Long prevcontractId = offer.getContract().getContractId();
            int term = termChecker.getTerm(offer.getModifiedDt(), now());

            if (afterThreeDays(term)) {
                updateContractPaper(contract, offer, prevcontractId);
            } else {
                throw new BeforeReOfferTermException(beforeThreeDays + term);
            }
        }
        sendContractEmail(offerAgent, player, offer);
        return offer;
    }

    private void updateContractPaper(Contract contract, Offer previousOffer, Long prevcontractId) {
        previousOffer.updateOffer(contract);
        contractRepository.deleteById(prevcontractId);
    }

    private void sendContractEmail(Agent offerAgent, Player player, Offer previousOffer) {
        ContractEmailDto emailDto =
                emailDto(offerAgent.getAgentName(),
                        player.getAgent().getEmail(),
                        previousOffer.getContract());
        mailService.sendContractMail(emailDto);
    }

    @Override
    public Page<Player> getClienetByAgentId(Long agentId, Pageable pageable) {
        return playerRepository.findClientByAgentId(agentId, pageable);
    }

    private Player findPlayerById(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(NoSuchDataException::new);
    }

    public boolean afterThreeDays(int elapsedDays) {
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
