package com.rent_a_car.agentski_bekend.security;


import com.rent_a_car.agentski_bekend.helper.TimeProvider;
import com.rent_a_car.agentski_bekend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    @Value("rent-a-car")
    private String APP_NAME;

    @Value("super-secret")
    private String SECRET;

    @Value("30000000")
    private int EXPIRES_IN;

    @Value("authorization")
    private String AUTH_HEADER;

    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    @Autowired
    TimeProvider timeProvider;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(String email){
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(email)
                .setAudience(generateAudience())
                .setIssuedAt(timeProvider.now())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    private String generateAudience() {
        return AUDIENCE_WEB;
    }

    private Date generateExpirationDate(){
        return new Date(timeProvider.now().getTime() + EXPIRES_IN);
    }

    public int getExpiredId(){ return EXPIRES_IN; }

    public String getToken(HttpServletRequest request) {

        String authHeader = getAuthHeaderFromHeader(request);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.setIssuedAt(timeProvider.now());
            refreshedToken = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate())
                    .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

    public String getEmailFromToken(String token) {

        String email;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            email = claims.getSubject();
        } catch (Exception e) {
            email = null;
        }
        return email;
    }

//    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
//        final Date created = this.getIssuedAtDateFromToken(token);
//        return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
//                && (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
//    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getEmailFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);

        return (username != null && username.equals(user.getUsername())
                /*&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())*/);

    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

//    private Boolean ignoreTokenExpiration(String token) {
//        String audience = this.getAudienceFromToken(token);
//        return (audience.equals(AUDIENCE_TABLET) || audience.equals(AUDIENCE_MOBILE));
//    }
//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = this.getExpirationDateFromToken(token);
//        return expiration.before(timeProvider.now());
//    }
}
