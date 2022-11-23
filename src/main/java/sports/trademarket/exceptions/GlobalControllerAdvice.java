package sports.trademarket.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import sports.trademarket.dto.ResponseDto;
import sports.trademarket.exceptions.spring.NoSuchDataException;

import javax.validation.ConstraintViolationException;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.*;
import static sports.trademarket.exceptions.spring.ErrorConstants.*;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Integer>> RequiredValueException(ConstraintViolationException e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(BAD_REQUEST.value(), requiredValue, fail);
        return new ResponseEntity<>(responseDto, responseHeader(), BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Integer>> NoSuchDataException(NoSuchDataException e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(BAD_REQUEST.value(), noSuchDataExist, fail);
        return new ResponseEntity<>(responseDto, responseHeader(), BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Integer>> NoHandlerFoundException(NoHandlerFoundException e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(NOT_FOUND.value(), pageNotFound, fail);
        return new ResponseEntity<>(responseDto, responseHeader(), NOT_FOUND);
    }

    private HttpHeaders responseHeader() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }

}
