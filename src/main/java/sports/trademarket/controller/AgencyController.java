package sports.trademarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sports.trademarket.dto.AgencyDto;
import sports.trademarket.dto.ResponseDto;
import sports.trademarket.entity.Agency;
import sports.trademarket.service.AgencyService;

import javax.validation.ConstraintViolationException;

import static sports.trademarket.dto.AgencyDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/agency")
public class AgencyController extends CommonController {

    private final AgencyService agencyService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<AgencyDto>> registerAgency(@RequestBody Agency agency)
            throws ConstraintViolationException {

        Agency registerAgency = agencyService.registerAgency(agency);
        return responseMsg(HttpStatus.OK, "Agency 등록완료", convert(registerAgency));

    }

    @GetMapping("/{agencyId}")
    public ResponseEntity<ResponseDto<AgencyDto>> getAgencyDetail(@PathVariable Long agencyId) {

        Agency agency = agencyService.findAgencyById(agencyId);
        return responseMsg(HttpStatus.OK, "Agency 조회완료", convert(agency));
    }

}
