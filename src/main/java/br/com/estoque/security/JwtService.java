package br.com.estoque.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
@Service
public class JwtService {
    private final SecretKey secretKey;
    private final long jwtExpiration;
    public JwtService(@Value("${app.jwt.secret}") String encodedSecret,
                      @Value("${app.jwt.expiration-ms:86400000}") long jwtExpiration) {
        byte[] bytes = Base64.getDecoder().decode(encodedSecret);
        if (bytes.length < 32) throw new IllegalArgumentException("JWT_SECRET deve ter no mínimo 32 bytes em Base64.");
        this.secretKey = Keys.hmacShaKeyFor(bytes);
        this.jwtExpiration = jwtExpiration;
    }
    public String extractUsername(String token) { return extractClaim(token, Claims::getSubject); }
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody());
    }
    public String generateToken(UserDetails userDetails) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtExpiration))
                .signWith(secretKey)
                .compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        Date expiration = extractClaim(token, Claims::getExpiration);
        return username.equals(userDetails.getUsername()) && expiration.after(new Date()) && userDetails.isEnabled();
    }
}
