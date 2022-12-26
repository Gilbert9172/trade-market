package sports.trademarket.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import sports.trademarket.dto.ResponseDto;

import java.nio.charset.StandardCharsets;

public class CommonController {

    public <T> ResponseEntity<ResponseDto<T>> responseMsg(HttpStatus status, String msg, T data) {
        ResponseDto<T> responseDto = ResponseDto.of(status.value(), msg, data);
        return new ResponseEntity<>(responseDto, responseHeader(), status);
    }

    private HttpHeaders responseHeader() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }
}
