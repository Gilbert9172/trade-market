package sports.trademarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.*;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.Contract;
import sports.trademarket.entity.Offer;
import sports.trademarket.entity.Player;
import sports.trademarket.service.AgentService;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import static sports.trademarket.utililty.JwtUtil.agentId;

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

    @GetMapping(value = "/{agentId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<AgentDetailDto>> getAgentDetail(@PathVariable Long agentId) {

        Agent agent = agentService.findAgentById(agentId);
        return responseMsg(OK, "Agent 조회완료", AgentDetailDto.toDto(agent));
    }

    @PutMapping(value = "/{agentId}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<AgentDetailDto>> updateAgentDetails(
                        @PathVariable Long agentId,
                        @RequestBody UpdateAgentDto detailDto) {

        Agent agent = agentService.updateDetils(agentId, detailDto);
        return responseMsg(OK, "Agent 상세수정 API", AgentDetailDto.toDto(agent));
    }

    @PostMapping("/offer/{playerId}")
    public ResponseEntity<ResponseDto<OfferDto>> offerTransfer(
            @PathVariable Long playerId,
            @RequestBody Contract contract) {

        Offer offer = agentService.offerTransfer(agentId(), playerId, contract);
        return responseMsg(OK, "Offer 제안하기 API", OfferDto.toDto(offer));
    }

    @GetMapping("/clients")
    public ResponseEntity<ResponseDto<Page<ClientDto>>> belongingPlayers(
            @PageableDefault(size=5, sort = "playerId", direction = ASC) Pageable pageable) {
        Page<Player> players = agentService.getClienetByAgentId(agentId(), pageable);
        return responseMsg(OK, "의뢰한 선수 목록API", players.map(ClientDto::of));
    }

}
