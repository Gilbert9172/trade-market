package sports.trademarket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sports.trademarket.entity.Player;

@Getter @Setter
@NoArgsConstructor
public class ClientDto {

    private String profileImgPath;
    private String name;
    private int age;
    private String teamName;
    private String position;

    private ClientDto (Player player) {
        profileImgPath = player.getProfileImg().getFullImgPath();
        name = player.getName();
        age = player.getAge();
        teamName = player.getTeam().getTeamName();
        position = player.getPosition().getPositionName();
    }

    public static ClientDto of(Player player) {
        return new ClientDto(player);
    }

}
