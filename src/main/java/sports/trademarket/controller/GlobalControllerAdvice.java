package sports.trademarket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sports.trademarket.exceptions.InputNotFoundException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<String> InputNotFoundException(InputNotFoundException e) {
        String msg = "Input Error";
        return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
    }

}
