package br.com.menu.menudigital.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTBasicAuthenticationFilter extends BasicAuthenticationFilter {

	public JWTBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String auth = request.getHeader("Authorization");

		if (auth == null || auth.isEmpty()) {
			chain.doFilter(request, response);
			return;
		}

		try {

			String[] authrization = auth.split("Bearer ");
			String jwt = authrization[1];

			DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256("thesecret")).build().verify(jwt);
			String email = decodedJwt.getClaim("email").asString();

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,
					null, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (JWTVerificationException e) {
		}

		chain.doFilter(request, response);
	}

}
