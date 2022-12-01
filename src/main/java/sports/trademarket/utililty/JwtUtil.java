package sports.trademarket.utililty;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sports.trademarket.entity.Agent;
import sports.trademarket.security.config.KeyConfig;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static sports.trademarket.dto.enumType.AuthConstants.AUTH_HEADER;
import static sports.trademarket.utililty.ServletReqUtil.getServletRequest;

public class JwtUtil {

    public static String generate(Agent agent) {
        return Jwts.builder()
                .setHeader(createJwtHeader())
                .setSubject(agent.getEmail())
                .setClaims(createClaims(agent))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(createAccessTokenExpirationTime())
                .signWith(createSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String reGenerate(Agent agent) {
        return Jwts.builder()
                .setHeader(createJwtHeader())
                .setSubject(agent.getEmail())
                .setClaims(createClaims(agent))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(createRefreshTokenExpirationTime())
                .signWith(createSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Map<String, Object> createJwtHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        return header;
    }

    private static Map<String, Object> createClaims(Agent agent) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("agentId", agent.getAgentId());
        claims.put("agentEml", agent.getEmail());
        claims.put("agentRole", agent.getRoleType());
        return claims;
    }

    private static Date createAccessTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);
        return calendar.getTime();
    }

    private static Date createRefreshTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }

    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KeyConfig.secretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public static String getTokenFromBearer(String header) {
        return header.split(" ")[1];
    }

    public static boolean isValidToken(String token) {
        return checkClaims(token);
    }

    // Refresh-Token Validation
    public static boolean isValidRefreshToken(String token) {
        return checkClaims(token);
    }

    private static boolean checkClaims(String token) {
        try {
            getAllClaimsFromToken(token);
            return true;
        } catch (JwtException | NullPointerException e) {
            return false;
        }
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(KeyConfig.secretKey))
                .build().parseClaimsJws(token).getBody();
    }

    public static String getEmailFromToken(String token) {
        return (String) getAllClaimsFromToken(token).get("agentEml");
    }

    // Get User Type From claims
    public static String getTypeFromToken(String token) {
        return (String) getAllClaimsFromToken(token).get("agentRole");
    }

    // Get User Id From claims
    public static Long getIdFromToken(String token) {
        return getAllClaimsFromToken(token).get("agentId", Long.class);
    }

    public static Long agentId() {
        String bearToken = getServletRequest().getHeader(AUTH_HEADER.getName());
        String token = getTokenFromBearer(bearToken);
        return getIdFromToken(token);
    }
}
