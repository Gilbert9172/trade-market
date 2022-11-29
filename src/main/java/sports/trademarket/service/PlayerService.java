package sports.trademarket.service;

import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.PlayerRegisterDto;

public interface PlayerService {

    void registerPlayer(PlayerRegisterDto registerDto, MultipartFile playerImg);

}
