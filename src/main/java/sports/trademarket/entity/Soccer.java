package sports.trademarket.entity;

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

    @Column(name = "PREFER_FOOT")
    private String preferFoot;

    @Enumerated(EnumType.STRING)
    @Column(name = "SKILL_LEVEL")
    private SkillLevel skillLevel;

    @Column(name = "POSITION")
    private String position;

    @Embedded
    private SoccerPlayerStats soccerPlayerStats;

    @Embedded
    private ContractCondition contractCondition;

}
