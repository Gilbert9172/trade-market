package sports.trademarket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sports.trademarket.entity.embaddedType.ContractCondition;
import sports.trademarket.entity.embaddedType.SoccerPlayerStats;
import sports.trademarket.entity.enumType.SkillLevel;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue("S")
@Table(name = "SOCCER")
public class Soccer extends Player {

    @Column(name = "FIFA_REG_NUM", unique = true)
    private String fifaRegNum;

    @Column(name = "PREFER_FOOT")
    private String preferFoot;

    @Enumerated(EnumType.STRING)
    @Column(name = "SKILL_LEVEL")
    private SkillLevel skillLevel;

    @Embedded
    private SoccerPlayerStats playerStats;

    @Embedded
    private ContractCondition contractCondition;

    @Builder
    private Soccer(Agent agent, Team team, Position position,
                   String name, int age, String fifaRegNum,
                  String preferFoot, SkillLevel skillLevel, SoccerPlayerStats playerStats,
                  ContractCondition contractCondition) {
        super(agent, team, position, name, age);
        this.fifaRegNum = fifaRegNum;
        this.preferFoot = preferFoot;
        this.skillLevel = skillLevel;
        this.playerStats = playerStats;
        this.contractCondition = contractCondition;
    }

}
