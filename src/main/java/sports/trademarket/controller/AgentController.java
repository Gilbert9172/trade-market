package sports.trademarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.AgentDetailDto;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.dto.ResponseDto;
import sports.trademarket.entity.Agent;
import sports.trademarket.service.AgentService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/agent")
public class AgentController extends CommonController {

    private final AgentService agentService;

    @PostMapping(value = "/join", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<Integer>> joinAgent(
                            @Valid @RequestPart AgentJoinDto agentJoin,
                            @RequestPart(required = false) MultipartFile profile) throws Exception {
        agentService.register(agentJoin, profile);
        return responseMsg(OK, "Agent 가입완료", 1);
    }

    @GetMapping(value = "/{agentId}", produces = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<AgentDetailDto>> getAgentDetail(@PathVariable Long agentId) {

        Agent agent = agentService.findAgentById(agentId);
        return responseMsg(OK, "Agent 조회완료", AgentDetailDto.toDto(agent));
    }

}
