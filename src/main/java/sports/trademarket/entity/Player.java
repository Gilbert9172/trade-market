package sports.trademarket.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sports.trademarket.entity.commonEntity.CommonTimeEntity;

import javax.persistence.*;

import static javax.persistence.DiscriminatorType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@DiscriminatorColumn(name = "SPORTS_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "PLAYER")
public class Player extends CommonTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "PLAYER_ID")
    private Long playerId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "AGENT_ID")
    private Agent agent;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "PROFILE_IMG_ID")
    private ProfileImg profileImg;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    @Column(name = "ACTIVE")
    private int active;

    public Player(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
