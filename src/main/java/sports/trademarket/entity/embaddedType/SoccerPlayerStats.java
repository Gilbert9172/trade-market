package sports.trademarket.entity.embaddedType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.*;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class SoccerPlayerStats {

    @Column(name = "GAOL")
    private int goal;

    @Column(name = "ASSIST")
    private int assist;

    @Column(name = "CLEAR")
    private int clear;

    @Column(name = "CLEAN_SHEET")
    private int cleanSheet;

}
