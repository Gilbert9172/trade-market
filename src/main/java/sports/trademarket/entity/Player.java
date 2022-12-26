package sports.trademarket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.entity.commonEntity.CommonTimeEntity;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static sports.trademarket.entity.ProfileImg.*;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "PROFILE_IMG_ID")
    private ProfileImg profileImg;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "POSITION_ID")
    private Position position;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    @Column(name = "ACTIVE")
    private int active;

    public Player(Long playerId, Agent agent, Team team, Position position,
                  String name, int age) {
        this.playerId = playerId;
        this.agent = agent;
        this.team = team;
        this.position = position;
        this.name = name;
        this.age = age;
        this.active = 1;
    }

    public void saveImgFile(MultipartFile file, String savePath) {
        this.profileImg = saveProfileImg(file, savePath);
    }

}
