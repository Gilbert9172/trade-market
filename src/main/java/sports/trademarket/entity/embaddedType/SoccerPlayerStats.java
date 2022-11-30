package sports.trademarket.entity.embaddedType;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.*;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SoccerPlayerStats {

    @Column(name = "GOAL")
    private int goal;

    @Column(name = "ASSIST")
    private int assist;

    @Column(name = "CLEAR")
    private int clear;

    @Column(name = "CLEAN_SHEET")
    private int cleanSheet;

    private SoccerPlayerStats(int goal, int assist, int clear, int cleanSheet) {
        this.goal = goal;
        this.assist = assist;
        this.clear = clear;
        this.cleanSheet = cleanSheet;
    }

    public static SoccerPlayerStats allStats(int goal, int assist, int clear, int cleanSheet) {
        return new SoccerPlayerStats(goal, assist, clear, cleanSheet);
    }
}
