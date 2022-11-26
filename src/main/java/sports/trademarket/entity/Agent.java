package sports.trademarket.entity;

import lombok.*;
import sports.trademarket.dto.AgentJoinDto;
import sports.trademarket.entity.commonEntity.CommonTimeEntity;
import sports.trademarket.entity.enumType.RoleType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static sports.trademarket.entity.enumType.RoleType.*;

@Entity
@Getter @Builder
@Table(name = "AGENT")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Agent extends CommonTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "AGENT_ID")
    private Long agentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE")
    private RoleType roleType = ROLE_USER;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "AGENCY_ID")
    private Agency agency;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "PROFILE_IMG_ID")
    private ProfileImg profileImg;

    @NotEmpty
    @Column(name = "AGENT_NAME")
    private String agentName;

    @Column(name = "CAREER")
    private int career;

    @NotEmpty
    @Column(name = "EMAIL", unique = true)
    private String email;

    @NotEmpty
    @Column(name = "PHONE", unique = true)
    private String phone;

    @NotEmpty
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACTIVE")
    @Builder.Default
    private int active = 1;

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
    }

    public static Agent toEntity(AgentJoinDto agentJoinDto) {
        return new Agent(agentJoinDto);
    }

}
