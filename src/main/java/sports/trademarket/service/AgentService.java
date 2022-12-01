package sports.trademarket.service;

import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.dto.UpdateAgentDto;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.Contract;
import sports.trademarket.entity.Offer;

import java.io.IOException;

public interface AgentService {

    void register(AgentJoinDto agentJoin, MultipartFile profile) throws IOException;

    Agent findAgentById(Long agentID);

    Agent updateDetils(Long agentId, UpdateAgentDto updateDto);

    Offer offerTransfer(Long playerId ,Contract contract);
}
