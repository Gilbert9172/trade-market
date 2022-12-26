package sports.trademarket.dto;

import lombok.*;

import javax.validation.constraints.Email;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class EmailDto {

    @Email
    private String receiverEmail;

}
