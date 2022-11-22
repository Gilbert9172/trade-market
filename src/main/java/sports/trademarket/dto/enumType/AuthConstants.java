package sports.trademarket.dto.enumType;

import lombok.Getter;

@Getter
public enum AuthConstants {

    AUTH_HEADER("Authorization"),
    RE_AUTH_HEADER("re_Authorization"),
    CONTENT_TYPE("Content-Type"),
    TOKEN_TYPE("BEARER"),
    ENCODING_TYPE("UTF-8");

    private final String name;

    AuthConstants(String name) {
        this.name = name;
    }
}
