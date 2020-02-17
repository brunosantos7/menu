package br.com.menu.menudigital.menu;

import java.util.Optional;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import br.com.menu.menudigital.restaurant.Restaurant;
import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.utils.TokenUtils;
import br.com.menu.menudigital.utils.exception.UnauthorizedModifyingException;

@Aspect
@Configuration
public class MenuAspect {

	private TokenUtils tokenUtils;
	
	private MenuRepository menuRepository;

	public MenuAspect(TokenUtils tokenUtils, MenuRepository menuRepository) {
		super();
		this.tokenUtils = tokenUtils;
		this.menuRepository = menuRepository;
	}


	@Before("execution(* br.com.menu.menudigital.menu.MenuController.save(..))")
	public void save(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();

		Long restaurantId = ((MenuDTO) args[0]).getRestaurantId();
		User user = tokenUtils.getUserOnJwsToken();

		if (!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList())
				.contains(restaurantId)) {
			throw new UnauthorizedModifyingException();
		}
	}


	@Before("execution(* br.com.menu.menudigital.menu.MenuController.update(..))")
	public void update(JoinPoint joinPoint) throws UnauthorizedModifyingException  {
		Object[] args = joinPoint.getArgs();
		Long restaurantId = ((MenuDTO) args[1]).getRestaurantId();
		User user = tokenUtils.getUserOnJwsToken();

		if (!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList())
				.contains(restaurantId)) {
			throw new UnauthorizedModifyingException();
		}
	}
	
	@Before("execution(* br.com.menu.menudigital.menu.MenuController.delete(..))")
	public void delete(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();

		Optional<Menu> menu = menuRepository.findById((Long) args[0]);
		
		if(menu.isPresent()) {
			
			Long restaurantId = menu.get().getRestaurantId();
			User user = tokenUtils.getUserOnJwsToken();

			if (!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList())
					.contains(restaurantId)) {
				throw new UnauthorizedModifyingException();
			}
		}
		
	}
	
}
