package sports.trademarket.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.dto.UpdateAgentDto;
import sports.trademarket.entity.commonEntity.CommonTimeEntity;
import sports.trademarket.entity.enumType.RoleType;

import javax.persistence.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static sports.trademarket.entity.enumType.RoleType.*;

@Entity
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Table(name = "AGENT")
public class Agent extends CommonTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "AGENT_ID")
    private Long agentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE")
    private RoleType roleType = ROLE_USER;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "AGENCY_ID")
    private Agency agency;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "PROFILE_IMG_ID")
    private ProfileImg profileImg;

    @Column(name = "AGENT_NAME")
    private String agentName;

    @Column(name = "CAREER")
    private int career;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PHONE", unique = true)
    private String phone;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACTIVE")
    private int active;

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public void setProfileImg(ProfileImg profileImg) {
        this.profileImg = profileImg;
    }

    private Agent(AgentJoinDto agentJoinDto) {
        agentName = agentJoinDto.getAgentName();
        email = agentJoinDto.getEmail();
        password = agentJoinDto.getPassword();
        phone = agentJoinDto.getPhone();
        career = agentJoinDto.getCareer();
        active = 1;
    }

    public static Agent toEntity(AgentJoinDto agentJoinDto) {
        return new Agent(agentJoinDto);
    }

    public void updateDetails(UpdateAgentDto updateDto, Agency newAgeny) {
        agency = newAgeny;
        phone = updateDto.getPhone();
        career = updateDto.getCareer();
    }

}
