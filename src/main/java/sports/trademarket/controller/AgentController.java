package sports.trademarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.dto.ResponseDto;
import sports.trademarket.service.AgentService;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/agent")
public class AgentController extends CommonController {

    private final AgentService agentService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDto<Integer>> joinAgent(
                            @Valid @RequestPart AgentJoinDto agentJoin,
                            @RequestPart(required = false) MultipartFile profile) throws Exception {

        agentService.register(agentJoin, profile);
        return responseMsg(OK, "Agent 가입완료", 1);
    }

}
