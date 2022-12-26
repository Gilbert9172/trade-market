package sports.trademarket.dto;

import lombok.*;

import javax.validation.constraints.Email;

import static lombok.AccessLevel.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class EmailGenerateDto {

    @Email
    private String receiverEmail;

    private String authKey;

    private String subject;

    private String text;

}

