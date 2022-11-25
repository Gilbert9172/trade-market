package sports.trademarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sports.trademarket.dto.AgencyDto;
import sports.trademarket.dto.ResponseDto;
import sports.trademarket.entity.Agency;
import sports.trademarket.exceptions.spring.NoSuchDataException;
import sports.trademarket.service.AgencyService;

import javax.validation.ConstraintViolationException;
import java.nio.charset.StandardCharsets;

import static sports.trademarket.dto.AgencyDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/agency")
public class AgencyController {

    private final AgencyService agencyService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<AgencyDto>> registerAgency(@RequestBody Agency agency)
            throws ConstraintViolationException {

        Agency registerAgency = agencyService.registerAgency(agency);

        ResponseDto<AgencyDto> result = ResponseDto.of(
                HttpStatus.OK.value(), "Agency 등록완료", convert(registerAgency)
        );
        return new ResponseEntity<>(result, responseHeader(), HttpStatus.OK);

    }

    @GetMapping("/{agencyId}")
    public ResponseEntity<ResponseDto<AgencyDto>> getAgencyDetail(@PathVariable Long agencyId) {

        Agency agency = agencyService.findAgencyById(agencyId);
        ResponseDto<AgencyDto> result = ResponseDto.of(
                HttpStatus.OK.value(), "Agency 조회완료", convert(agency)
        );
        return new ResponseEntity<>(result, responseHeader(), HttpStatus.OK);

    }

    private HttpHeaders responseHeader() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }

}
