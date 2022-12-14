package sports.trademarket.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import sports.trademarket.controller.CommonController;
import sports.trademarket.dto.ResponseDto;
import sports.trademarket.exceptions.spring.*;

import javax.validation.ConstraintViolationException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLSyntaxErrorException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import static org.springframework.http.HttpStatus.*;
import static sports.trademarket.exceptions.spring.ErrorConstants.*;

@RestControllerAdvice
public class GlobalControllerAdvice extends CommonController {

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<ResponseDto<Integer>> ValidationException(Exception e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(BAD_REQUEST.value(), requiredValue, fail);
        return new ResponseEntity<>(responseDto, responseHeader(), BAD_REQUEST);
    }

    @ExceptionHandler({SQLSyntaxErrorException.class})
    public ResponseEntity<ResponseDto<Integer>> SQLSyntaxErrorException(Exception e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(BAD_REQUEST.value(), checkValidation, fail);
        return new ResponseEntity<>(responseDto, responseHeader(), BAD_REQUEST);
    }


    @ExceptionHandler({NoSuchDataException.class})
    public ResponseEntity<ResponseDto<Integer>> NoSuchDataException(Exception e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(BAD_REQUEST.value(), noSuchDataExist, fail);
        return new ResponseEntity<>(responseDto, responseHeader(), BAD_REQUEST);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ResponseDto<Integer>> NoHandlerFoundException(Exception e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(NOT_FOUND.value(), pageNotFound, fail);
        return new ResponseEntity<>(responseDto, responseHeader(), NOT_FOUND);
    }

    @ExceptionHandler({SaveFileException.class})
    public ResponseEntity<ResponseDto<Integer>> IOException(Exception e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(BAD_REQUEST.value(), e.getMessage(), fail);
        return new ResponseEntity<>(responseDto, responseHeader(), NOT_FOUND);
    }

    @ExceptionHandler({IllegalFileNameException.class, EmailVerifiedException.class})
    public ResponseEntity<ResponseDto<Integer>> NullPointException(Exception e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(BAD_REQUEST.value(), e.getMessage(), fail);
        return new ResponseEntity<>(responseDto, responseHeader(), BAD_REQUEST);
    }

    @ExceptionHandler({DuplicationException.class})
    public ResponseEntity<ResponseDto<Integer>> DuplicationException(Exception e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(BAD_REQUEST.value(), e.getMessage(), fail);
        return new ResponseEntity<>(responseDto, responseHeader(), BAD_REQUEST);
    }

    @ExceptionHandler({BeforeReOfferTermException.class})
    public ResponseEntity<ResponseDto<Integer>> BeforeReOFferTermException(Exception e) {
        return responseMsg(BAD_REQUEST, e.getMessage(), fail);
    }

    private HttpHeaders responseHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }

}
