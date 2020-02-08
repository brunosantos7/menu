package br.com.menu.menudigital.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.user.UserRepository;

@Configuration
public class TokenUtils {
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserOnJwsToken() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		String auth = request.getHeader("Authorization");
		
		if(auth == null || auth.isEmpty()) {
			throw new BadCredentialsException("Voce precisa enviar um token");
		}
		
		String[] authrization = auth.split("Bearer ");
		String jwt = authrization[1];
		
		DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256("thesecret")).build().verify(jwt);
		String email = decodedJwt.getClaim("email").asString();

		return userRepository.findByEmailWithRestaurants(email);
	}

}
