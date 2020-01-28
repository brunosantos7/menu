package br.com.menu.menudigital.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

		Cookie[] cookies = request.getCookies();
		String jwt = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("token")) {
				jwt = cookie.getValue();
			}
		}
		DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256("thesecret")).build().verify(jwt);
		String username = decodedJwt.getClaim("username").asString();

		return userRepository.findByUsernameWithRestaurants(username);
	}

}
