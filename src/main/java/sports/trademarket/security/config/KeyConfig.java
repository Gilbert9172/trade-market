package sports.trademarket.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeyConfig {

    @Value("${secret.jwt.key}")
    private String initialization;

    public static String secretKey;

    @Value("${secret.jwt.key}")
    public void setSecretKey(String initialization) {
        KeyConfig.secretKey = initialization;
    }

}
