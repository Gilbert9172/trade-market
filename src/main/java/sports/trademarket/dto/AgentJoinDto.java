package sports.trademarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentJoinDto {

    @NotNull
    private Long agencyId;

    @NotEmpty
    private String agentName;

    @Email
    private String email;

    @Length(min = 6)
    private String password;

    @NotEmpty
    private String phone;

    private int career;

}
