package sports.trademarket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Builder
@Getter @Setter
public class EmailDto {

    @Email
    private String receiverEmail;

    private String authKey;

    private String subject;

    private String text;

}
