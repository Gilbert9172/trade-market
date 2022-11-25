package sports.trademarket.service;

import sports.trademarket.dto.EmailDto;

public interface MailService {

    void sendMail(EmailDto emailDto);

    Integer verifyEmail(EmailDto emailDto);

}
