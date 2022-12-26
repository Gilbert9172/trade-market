package sports.trademarket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sports.trademarket.entity.Agent;

@Getter @Setter
@NoArgsConstructor
public class AgentDetailDto {

    private String imgPath;
    private String agencyName;
    private String agentName;
    private String email;
    private String phone;
    private int career;

    private AgentDetailDto(Agent agent) {
        imgPath = agent.getProfileImg().getFullImgPath();
        agencyName = agent.getAgency().getAgencyName();
        agentName = agent.getAgentName();
        email = agent.getEmail();
        phone = agent.getPhone();
        career = agent.getCareer();
    }

    public static AgentDetailDto toDto(Agent agent) {
        return new AgentDetailDto(agent);
    }

}
