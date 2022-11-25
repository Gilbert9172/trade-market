package sports.trademarket.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import sports.trademarket.dto.ResponseDto;
import sports.trademarket.exceptions.spring.IllegalFileNameException;
import sports.trademarket.exceptions.spring.NoSuchDataException;
import sports.trademarket.exceptions.spring.SaveFileException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.*;
import static sports.trademarket.exceptions.spring.ErrorConstants.*;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ResponseDto<Integer>> ValidationException(ValidationException e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(BAD_REQUEST.value(), requiredValue, fail);
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
        ResponseDto<Integer> responseDto = ResponseDto.of(BAD_REQUEST.value(), "파일명을 확인해 주세요!", fail);
        return new ResponseEntity<>(responseDto, responseHeader(), NOT_FOUND);
    }

    @ExceptionHandler({IllegalFileNameException.class})
    public ResponseEntity<ResponseDto<Integer>> NullPointException(Exception e) {
        ResponseDto<Integer> responseDto = ResponseDto.of(BAD_REQUEST.value(), "파일명이 옳바르지 않습니다.", fail);
        return new ResponseEntity<>(responseDto, responseHeader(), BAD_REQUEST);
    }


    private HttpHeaders responseHeader() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }

}
