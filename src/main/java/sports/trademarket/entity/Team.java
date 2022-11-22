package sports.trademarket.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sports.trademarket.entity.commonEntity.CommonTimeEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Team extends CommonTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TEAM_ID")
    private Long teamId;

    @Column(name = "TEAM_NAME")
    private String teamName;

    @Column(name = "HISTORY")
    private String history;

    @Column(name = "HOME_CITY")
    private String homeCity;

    @Column(name = "OWNER_NAME")
    private String ownerName;

    @Column(name = "COACH_NAME")
    private String coachName;

    @Column(name = "COACH_EMAIL")
    private String coachEmail;

    @Column(name = "ACTIVE")
    private int active;

}
