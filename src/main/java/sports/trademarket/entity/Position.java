package sports.trademarket.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "`POSITION`")
public class Position {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "POSITION_ID")
    private Long positionId;

    @Column(name = "POSITION_NAME")
    private String positionName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Position parent;

    @OneToMany(mappedBy = "parent")
    List<Position> positions = new ArrayList<>();

    @OneToMany(mappedBy = "position")
    List<Player> players = new ArrayList<>();

    public Position(Long positionId, String positionName, Position parent) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.parent = parent;
    }
}
