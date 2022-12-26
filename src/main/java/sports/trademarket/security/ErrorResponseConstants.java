package sports.trademarket.security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import sports.trademarket.dto.ResponseDto;

import static org.springframework.http.HttpStatus.*;

public class ErrorResponseConstants {

    private static final Integer success                = 1;
    private static final Integer fail                   = 0;
    private static final String loginSuccess            = "로그인 성공";
    private static final String loginFail               = "로그인 실패";
    private static final String wrongCertification      = "자격이 증명되지 않았습니다.";
    private static final String wrongPassword           = "올바르지 않은 비밀번호 입니다.";
    private static final String noSuchUser              = "등록되지 않은 회원입니다.";
    private static final String noInput                 = "부적합한 입력입니다.";
    private static final String loginAgain              = "다시 로그인 해주세요.";
    private static final String emptyToken              = "헤더에서 토큰을 확인해 주세요.";
    private static final String hasNoAuthorities        = "적절하지 않은 접근입니다.";
    private static final String newAccessToken          = "기존 토큰이 만료되어 다시 발행했습니다.";

    public static String loginSuccessMessage() {
        return msgResponseTemplate(OK, loginSuccess, success);
    }

    public static String loginFail() {
        return msgResponseTemplate(BAD_REQUEST, loginFail, fail);
    }

    public static String wrongCertification() {
        return msgResponseTemplate(UNAUTHORIZED, wrongCertification, fail);
    }

    public static String wrongPassword() {
        return msgResponseTemplate(BAD_REQUEST, wrongPassword, fail);
    }

    public static String noSuchUser() {
        return msgResponseTemplate(UNAUTHORIZED, noSuchUser, fail);
    }

    public static String noInput() {
        return msgResponseTemplate(BAD_REQUEST, noInput, fail);
    }

    public static String loginAgain() {
        return msgResponseTemplate(UNAUTHORIZED, loginAgain, fail);
    }

    public static String emptyToken() {
        return msgResponseTemplate(UNAUTHORIZED, emptyToken, fail);
    }

    public static String hasNoAuthorities() {
        return msgResponseTemplate(FORBIDDEN, hasNoAuthorities, fail);
    }

    public static String newAccessToken(String token) {
        return msgResponseTemplate(OK, newAccessToken, token);
    }

    private static <T> String msgResponseTemplate(HttpStatus httpStatus, String msg, T data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(ResponseDto.of(httpStatus.value(), msg, data));
    }

}
