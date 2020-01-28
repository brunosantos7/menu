package br.com.menu.menudigital.menu;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.BadAttributeValueExpException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.menu.menudigital.restaurant.Restaurant;
import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.user.UserRepository;

@Aspect
@Configuration
public class MenuAspect {

	private UserRepository userRepository;

	private MenuRepository menuRepository;

	public MenuAspect(UserRepository userRepository, MenuRepository menuRepository) {
		super();
		this.userRepository = userRepository;
		this.menuRepository = menuRepository;
	}

	@Before("execution(* br.com.menu.menudigital.menu.MenuController.save(..))")
	public void save(JoinPoint joinPoint) throws BadAttributeValueExpException {
		Object[] args = joinPoint.getArgs();
		Long restaurantId = null;

		restaurantId = ((MenuDTO) args[0]).getRestaurantId();
		User user = getUserOnJwsToken();

		if (!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList())
				.contains(restaurantId)) {
			throw new BadAttributeValueExpException("Parece que esse restaurante nao e seu.");
		}
	}


	@Before("execution(* br.com.menu.menudigital.menu.MenuController.update(..))")
	public void update(JoinPoint joinPoint) throws BadAttributeValueExpException {
		Object[] args = joinPoint.getArgs();
		Long restaurantId = null;

		restaurantId = ((MenuDTO) args[1]).getRestaurantId();
		User user = getUserOnJwsToken();

		if (!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList())
				.contains(restaurantId)) {
			throw new BadAttributeValueExpException("Parece que esse restaurante nao e seu.");
		}
	}
	
	@Before("execution(* br.com.menu.menudigital.menu.MenuController.delete(..))")
	public void delete(JoinPoint joinPoint) throws BadAttributeValueExpException {
		Object[] args = joinPoint.getArgs();
		Long restaurantId = null;

		Optional<Menu> menu = menuRepository.findById((Long) args[0]);
		
		if(menu.isPresent()) {
			
			restaurantId = menu.get().getRestaurantId();
			User user = getUserOnJwsToken();

			if (!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList())
					.contains(restaurantId)) {
				throw new BadAttributeValueExpException("Parece que esse restaurante nao e seu.");
			}
		}
		
	}
	
	private User getUserOnJwsToken() {
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

		User user = userRepository.findByUsernameWithRestaurants(username);
		return user;
	}
}
