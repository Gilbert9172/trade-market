package sports.trademarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.dto.PlayerRegisterDto;
import sports.trademarket.dto.ResponseDto;
import sports.trademarket.service.PlayerService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/player")
public class PlayerController extends CommonController {

    private final PlayerService playerService;

    @PostMapping(value = "/register", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<Integer>> registerPlayer(
                @Valid @RequestPart PlayerRegisterDto registerDto,
                @RequestPart MultipartFile playerImg) {

        playerService.registerPlayer(registerDto, playerImg);
        return responseMsg(OK, "Player 등록 API", 1);
    }


}
