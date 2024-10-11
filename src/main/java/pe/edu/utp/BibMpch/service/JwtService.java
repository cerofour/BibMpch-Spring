package pe.edu.utp.BibMpch.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	public String getToken(UserDetails user) {
		return getToken(new HashMap<>(), user, jwtExpiration);
	}

	private String getToken(Map<String, Object> claims, UserDetails user, Long expiration) {
		return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public long getExpirationTime() {
		return jwtExpiration;
	}

	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String getUsernameFromToken(String token) {
		return getClaim(token, Claims::getSubject);
	}

	private Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		System.out.println("isTokenValid?");
		System.out.println(getExpiration(token));
		System.out.println(new Date());
		final String username = getUsernameFromToken(token);
		boolean x =(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		if (x)
			System.out.println("token valid");
		return x;
	}

	private Claims getAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private boolean isTokenExpired(String token) {
		boolean x = getExpiration(token).before(new Date());
		if (x)
			System.out.println("token expired");
		return x;
	}
}
