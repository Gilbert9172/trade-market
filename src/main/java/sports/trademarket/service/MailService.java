package sports.trademarket.service;

import sports.trademarket.dto.EmailDto;
import sports.trademarket.dto.EmailVerifyDto;

public interface MailService {

    void sendMail(EmailDto emailDto);

    Integer verifyEmail(EmailVerifyDto emailDto);

}
