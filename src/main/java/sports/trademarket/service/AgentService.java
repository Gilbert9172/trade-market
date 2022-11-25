package sports.trademarket.service;

import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.AgentJoinDto;

import java.io.IOException;

public interface AgentService {

    void register(AgentJoinDto agentJoin, MultipartFile profile) throws IOException;

}
