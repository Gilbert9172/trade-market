package sports.trademarket.entity;

import lombok.*;
import sports.trademarket.entity.commonEntity.CommonTimeEntity;
import sports.trademarket.entity.enumType.RoleType;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

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
    private RoleType roleType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "AGENCY_ID")
    private Agency agency;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "PROFILE_IMG_ID")
    private ProfileImg profileImg;

    @Column(name = "AGENT_NAME")
    private String agentName;

    @Column(name = "CAREER")
    private int career;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACTIVE")
    private int active;

}
