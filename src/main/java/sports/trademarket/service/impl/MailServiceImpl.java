package sports.trademarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sports.trademarket.dto.EmailDto;
import sports.trademarket.exceptions.spring.EmailVerifiedException;
import sports.trademarket.service.MailService;
import sports.trademarket.utililty.RedisUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Optional;

import static sports.trademarket.exceptions.spring.ErrorConstants.failedVerifyEmail;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final RedisUtil redisUtil;
    private final JavaMailSender javaMailSender;

    final int codeLength = 6;
    final String subject = "안녕하세요 Trade-Market 입니다.";
    final String text = "이메일 인증을 위한 인증번호입니다. >>> ";

    @Override
    public void sendMail(EmailDto emailDto) {

        String authKey = generateRandomCode(codeLength);


        EmailDto email = EmailDto.builder()
                .receiverEmail(emailDto.getReceiverEmail())
                .authKey(authKey)
                .subject(subject)
                .text(text + authKey)
                .build();

        sendAuthEmail(email);
    }

    @Override
    public Integer verifyEmail(EmailDto emailDto) {

        String requestEmail = Optional.ofNullable(redisUtil.getData(emailDto.getAuthKey()))
                .orElseThrow(() -> new EmailVerifiedException(failedVerifyEmail));

        return isEqal(requestEmail, emailDto.getReceiverEmail()) ? 1 : 0 ;
    }

    private boolean isEqal(String receiverEmail, String requestEmail) {
        return requestEmail.equals(receiverEmail);
    }

    private void sendAuthEmail(EmailDto emailDto) {
        generateAndSendEmail(emailDto);
        redisUtil.setDataExpire(emailDto.getAuthKey(), emailDto.getReceiverEmail(), 60 * 3L);
    }

    private void generateAndSendEmail(EmailDto emailDto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(emailDto.getReceiverEmail());
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getText(), true);
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
