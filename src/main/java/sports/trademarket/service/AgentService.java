package sports.trademarket.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.dto.UpdateAgentDto;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.Contract;
import sports.trademarket.entity.Offer;
import sports.trademarket.entity.Player;

import java.io.IOException;

public interface AgentService {

    void register(AgentJoinDto agentJoin, MultipartFile profile) throws IOException;

    Agent findAgentById(Long agentID);

    Agent updateDetils(Long agentId, UpdateAgentDto updateDto);

    Offer offerTransfer(Long agentId, Long playerId ,Contract contract);

    Page<Player> getClienetByAgentId(Long agentId, Pageable pageable);
}
