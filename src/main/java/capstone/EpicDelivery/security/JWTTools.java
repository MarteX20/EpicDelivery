package capstone.EpicDelivery.security;

import capstone.EpicDelivery.Users.User;
import capstone.EpicDelivery.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("PLL9KDLP467QJYCH8X8MYZLP1HK1J3Z6")
    private String secret;

    public String createToken(User u) {
        String token = Jwts.builder().setSubject(u.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                // token
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
        return token;
    }

    public void verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnauthorizedException("Il token non è valido! Per favore effettua di nuovo il login");
        }
    }

    public String extractSubject(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token)
                .getBody().getSubject();
    }
}