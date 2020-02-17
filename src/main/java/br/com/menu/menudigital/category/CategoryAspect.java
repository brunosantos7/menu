package br.com.menu.menudigital.category;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.BadAttributeValueExpException;

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
public class CategoryAspect {

	private TokenUtils tokenUtils;
	
	private CategoryRepository categoryRepository;

	public CategoryAspect(TokenUtils tokenUtils, CategoryRepository categoryRepository) {
		super();
		this.tokenUtils = tokenUtils;
		this.categoryRepository = categoryRepository;
	}

	@Before("execution (* br.com.menu.menudigital.category.CategoryController.save(..))")
	public void save(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();
		Long restaurantId = ((CategoryDTO) args[0]).getRestaurantId();

		User user = tokenUtils.getUserOnJwsToken();

		if (!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList())
				.contains(restaurantId)) {
			throw new UnauthorizedModifyingException();
		}
	}

	@Before("execution (* br.com.menu.menudigital.category.CategoryController.update(..))")
	public void update(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();
		Long restaurantId = ((CategoryDTO) args[1]).getRestaurantId();

		User user = tokenUtils.getUserOnJwsToken();

		if (!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList())
				.contains(restaurantId)) {
			throw new UnauthorizedModifyingException();
		}
	}

	@Before("execution (* br.com.menu.menudigital.category.CategoryController.updateCategoryImage(..))")
	public void updateCategoryImage(JoinPoint joinPoint) throws BadAttributeValueExpException {
		Object[] args = joinPoint.getArgs();
		Long restaurantId = ((CategoryDTO) args[1]).getRestaurantId();

		User user = tokenUtils.getUserOnJwsToken();

		if (!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList())
				.contains(restaurantId)) {
			throw new BadAttributeValueExpException("Parece que esse restaurante nao e seu.");
		}
	}

	@Before("execution (* br.com.menu.menudigital.category.CategoryController.delete(..))")
	public void delete(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();
		Long categoryId = (Long) args[0];
		
		Optional<Category> category = categoryRepository.findById(categoryId);
		
		if(category.isPresent()) {
			Long restaurantId = category.get().getRestaurantId();
			
			User user = tokenUtils.getUserOnJwsToken();

			if (!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList())
					.contains(restaurantId)) {
				throw new UnauthorizedModifyingException();
			}
		}
	}
}
