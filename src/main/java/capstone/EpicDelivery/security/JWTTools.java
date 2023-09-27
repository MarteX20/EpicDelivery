package capstone.EpicDelivery.security;

import capstone.EpicDelivery.Users.User;
import capstone.EpicDelivery.Users.UsersRepository;
import capstone.EpicDelivery.exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("PLL9KDLP467QJYCH8X8MYZLP1HK1J3Z6")
    private String secret;

    @Value("quu")
    private String jwtCookie;
    private final UsersRepository usersRepository;

    public JWTTools(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getId().toString());


        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("tel", user.getTel());
        claims.put("address", user.getAddress());


        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Scade dopo 7 giorni
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
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