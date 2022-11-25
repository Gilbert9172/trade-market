package sports.trademarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentJoinDto {

    private Long agencyId;
    private String agentName;
    private String email;

    @Length(min = 6)
    private String password;
    private String phone;
    private int career;

}
