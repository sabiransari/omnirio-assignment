package com.omnirio.assignment.user.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.omnirio.assignment.user.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHandler {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Value("security.jwt.token.secret-key:the_secret_key_xyzabc")
	private String secretKey;

	@Value("${security.jwt.token.expire-length:3600000}")
	private Long validityInMillis;
	
	@PostConstruct
	public void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	public String createToken(String userId, List<Role> roles) {
		Claims claims = Jwts.claims().setSubject(userId);
		claims.put("auth", roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority()))
				.filter(Objects::nonNull).collect(Collectors.toList()));
		
		Date now = new Date();
		Date expiry = new Date(now.getTime() + validityInMillis);
		
		return Jwts.builder()
		        .setClaims(claims)
		        .setIssuedAt(now)
		        .setExpiration(expiry)
		        .signWith(SignatureAlgorithm.HS256, secretKey)
		        .compact();
	}
	
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
	    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
	    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
	      return bearerToken.substring(7);
	    }
	    return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
	    } catch (JwtException | IllegalArgumentException e) {
	    	e.printStackTrace();
	    	throw new RuntimeException("Invalid token");
	    }
	}
}
