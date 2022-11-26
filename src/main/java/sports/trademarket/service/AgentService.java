package sports.trademarket.service;

import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.entity.Agent;

import java.io.IOException;

public interface AgentService {

    void register(AgentJoinDto agentJoin, MultipartFile profile) throws IOException;

    Agent findAgentById(Long agentID);

}
