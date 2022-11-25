package sports.trademarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentJoinDto {

    private Long agencyId;
    private String agentName;
    private String email;
    private String password;
    private String phone;
    private int career;

}
