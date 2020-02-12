package br.com.menu.menudigital.restaurant;

import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.utils.TokenUtils;
import br.com.menu.menudigital.utils.UnauthorizedModifyingException;

@Aspect
@Configuration
public class RestaurantAspect {

	private TokenUtils tokenUtils;
	
	public RestaurantAspect(TokenUtils tokenUtils) {
		super();
		this.tokenUtils = tokenUtils;
	}

	@Before("execution (* br.com.menu.menudigital.restaurant.RestaurantController.updateRestaurant(..))")
	public void update(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();
		Long restaurantId = (Long)args[0];
		
		User user = tokenUtils.getUserOnJwsToken();
		
		if(!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList()).contains(restaurantId)){
			throw new UnauthorizedModifyingException();
		}
	}
	
	@Before("execution (* br.com.menu.menudigital.restaurant.RestaurantController.updateRestaurantImage(..))")
	public void updateRestaurantImage(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();
		
		Long restaurantId = (Long)args[0];
		
		User user = tokenUtils.getUserOnJwsToken();
		
		if(!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList()).contains(restaurantId)){
			throw new UnauthorizedModifyingException();
		}
	}
	
	@Before("execution (* br.com.menu.menudigital.restaurant.RestaurantController.deleteRestaurant(..))")
	public void delete(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();
		
		Long restaurantId = (Long)args[0];
		
		User user = tokenUtils.getUserOnJwsToken();
		
		if(!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList()).contains(restaurantId)){
			throw new UnauthorizedModifyingException();
		}
	}
	
	@Before("execution (* br.com.menu.menudigital.restaurant.RestaurantController.approvalRequest(..))")
	public void approvalRequest(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();
		
		Long restaurantId = (Long)args[0];
		
		User user = tokenUtils.getUserOnJwsToken();
		
		if(!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList()).contains(restaurantId)){
			throw new UnauthorizedModifyingException();
		}
	}
	
}
