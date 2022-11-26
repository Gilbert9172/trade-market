package sports.trademarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sports.trademarket.dto.AgencyDetailDto;
import sports.trademarket.dto.AgencyJoinDto;
import sports.trademarket.dto.ResponseDto;
import sports.trademarket.entity.Agency;
import sports.trademarket.service.AgencyService;

import javax.validation.Valid;

import static sports.trademarket.dto.AgencyDetailDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/agency")
public class AgencyController extends CommonController {

    private final AgencyService agencyService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Integer>> registerAgency(@Valid @RequestBody AgencyJoinDto joinDto) {

        agencyService.registerAgency(joinDto);
        return responseMsg(HttpStatus.OK, "Agency 등록완료", 1);
    }

    @GetMapping("/{agencyId}")
    public ResponseEntity<ResponseDto<AgencyDetailDto>> getAgencyDetail(@PathVariable Long agencyId) {

        Agency agency = agencyService.findAgencyById(agencyId);
        return responseMsg(HttpStatus.OK, "Agency 조회완료", toDto(agency));
    }

}
