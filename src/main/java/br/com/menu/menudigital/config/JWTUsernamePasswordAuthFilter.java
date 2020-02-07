package br.com.menu.menudigital.config;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User user = (User) authResult.getPrincipal();
		String email = user.getUsername();

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		String jwt = JWT.create().withClaim("email", email).withIssuedAt(now)
				.withExpiresAt(new Date(nowMillis + TimeUnit.DAYS.toMillis(1))).sign(Algorithm.HMAC256("thesecret"));
		
		Cookie cookie = new Cookie("token", jwt);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		
		response.addCookie(cookie);
		
		super.successfulAuthentication(request, response, chain, authResult);
	}
}
