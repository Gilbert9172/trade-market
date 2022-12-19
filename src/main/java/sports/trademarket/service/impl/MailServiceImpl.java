package sports.trademarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sports.trademarket.dto.ContractEmailDto;
import sports.trademarket.dto.EmailDto;
import sports.trademarket.dto.EmailGenerateDto;
import sports.trademarket.dto.EmailVerifyDto;
import sports.trademarket.exceptions.spring.EmailVerifiedException;
import sports.trademarket.service.MailService;
import sports.trademarket.utililty.RedisUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Optional;

import static sports.trademarket.exceptions.spring.ErrorConstants.failedVerifyEmail;

@Service
@Transactional
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final RedisUtil redisUtil;
    private final JavaMailSender javaMailSender;



    @Override
    public void sendMail(EmailDto emailDto) {

        final int codeLength = 6;
        final String subject = "안녕하세요 Trade-Market 입니다.";
        final String text = "이메일 인증을 위한 인증번호입니다. >>> ";

        String authKey = generateRandomCode(codeLength);


        EmailGenerateDto generateDto = EmailGenerateDto.builder()
                .receiverEmail(emailDto.getReceiverEmail())
                .authKey(authKey)
                .subject(subject)
                .text(text + authKey)
                .build();

        sendAuthEmail(generateDto);
    }

    @Override
    public void sendContractMail(ContractEmailDto contractEmailDto) {

        String currencyUnit = contractEmailDto.getCurrencyUnit().name();

        final String subject = "안녕하세요 에이전트 "+ contractEmailDto.getAgentName() + "입니다.";
        final String text =
                "<h1>제안서 입니다.</h1>"
                + "<br>"
                + "이적료 : " + contractEmailDto.getTransferFee()  + currencyUnit + "<br>"
                + "연봉 : " + contractEmailDto.getPayment() + currencyUnit + "<br>"
                + "계약 형태 : " + contractEmailDto.getContractDivision().name() + "<br>"
                + "계약 기간 : " + contractEmailDto.getContractYear() + "년 " + contractEmailDto.getContractMonth() + "개월" + "<br>"
                + "추가 옵션 : " + contractEmailDto.getOptions();

        EmailGenerateDto generateDto = EmailGenerateDto.builder()
                .receiverEmail(contractEmailDto.getReceiverEmail())
                .subject(subject)
                .text(text)
                .build();

        generateAndSendEmail(generateDto);
    }

    @Override
    public Integer verifyEmail(EmailVerifyDto verifyDto) {

        String requestEmail = Optional.ofNullable(redisUtil.getData(verifyDto.getAuthKey()))
                .orElseThrow(() -> new EmailVerifiedException(failedVerifyEmail));

        return isEqal(requestEmail, verifyDto.getReceiverEmail()) ? 1 : 0 ;
    }

    private boolean isEqal(String receiverEmail, String requestEmail) {
        return requestEmail.equals(receiverEmail);
    }

    private void sendAuthEmail(EmailGenerateDto generateDto) {
        generateAndSendEmail(generateDto);
        redisUtil.setDataExpire(generateDto.getAuthKey(), generateDto.getReceiverEmail(), 60 * 3L);
    }

    private void generateAndSendEmail(EmailGenerateDto generateDto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(generateDto.getReceiverEmail());
            helper.setSubject(generateDto.getSubject());
            helper.setText(generateDto.getText(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String generateRandomCode(int len) {

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }
}
