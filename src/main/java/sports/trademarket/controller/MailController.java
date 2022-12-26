package sports.trademarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sports.trademarket.dto.EmailDto;
import sports.trademarket.dto.EmailVerifyDto;
import sports.trademarket.dto.ResponseDto;
import sports.trademarket.service.MailService;

@RestController
@RequestMapping("/v1/email")
@RequiredArgsConstructor
public class MailController extends CommonController {

    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<ResponseDto<Integer>> sendCertificationCode(@RequestBody EmailDto emailDto) {
        mailService.sendMail(emailDto);
        return responseMsg(HttpStatus.OK, "전송 완료", 1);
    }

    @PostMapping("/verify")
    public ResponseEntity<ResponseDto<Integer>> verifyEmail(@RequestBody EmailVerifyDto emailDto) {
        return responseMsg(HttpStatus.OK, "인증 결과", mailService.verifyEmail(emailDto));

    }
}
