package com.ongraph.usermanagementapp.security.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ongraph.usermanagementapp.security.model.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {
	
	
	@Value("${ongraph.authentication.secretKey}")
	private String jwtSecret;
	
	@Value("${ongraph.authentication.tokentExpTimeInMs}")
	private int jwtExpirationMs;
	
	
	public String generateToken(Authentication authentication) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis((new Date()).getTime()+jwtExpirationMs);
		var userPrincipal=(UserDetailsImpl) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(calendar.getTime())
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
						
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}catch (SignatureException e) {
		      log.error("Invalid JWT signature: {}", e.getMessage());
	    } catch (MalformedJwtException e) {
	    	log.error("Invalid JWT token: {}", e.getMessage());
	    } catch (ExpiredJwtException e) {
	    	log.error("JWT token is expired: {}", e.getMessage());
	    } catch (UnsupportedJwtException e) {
	    	log.error("JWT token is unsupported: {}", e.getMessage());
	    } catch (IllegalArgumentException e) {
	    	log.error("JWT claims string is empty: {}", e.getMessage());
	    }
		return false;
	}
}

