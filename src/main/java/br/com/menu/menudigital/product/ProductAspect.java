package br.com.menu.menudigital.product;

import java.util.Optional;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import br.com.menu.menudigital.restaurant.Restaurant;
import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.utils.TokenUtils;
import br.com.menu.menudigital.utils.UnauthorizedModifyingException;

@Aspect
@Configuration
public class ProductAspect {
	
	private TokenUtils tokenUtils;
	
	private ProductRepository productRepository;
	
	public ProductAspect(TokenUtils tokenUtils, ProductRepository productRepository) {
		super();
		this.tokenUtils = tokenUtils;
		this.productRepository = productRepository;
	}

	@Before("execution (* br.com.menu.menudigital.product.ProductController.save(..))")
	public void save(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();
		Long restaurantId = ((ProductDTO)args[0]).getRestaurantId();
		
		User user = tokenUtils.getUserOnJwsToken();
		
		if(!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList()).contains(restaurantId)) {
			throw new UnauthorizedModifyingException();
		}
	}

	@Before("execution (* br.com.menu.menudigital.product.ProductController.update(..))")
	public void update(JoinPoint joinPoint) throws UnauthorizedModifyingException  {
		Object[] args = joinPoint.getArgs();
		Long restaurantId = ((ProductDTO)args[1]).getRestaurantId();
		
		User user = tokenUtils.getUserOnJwsToken();
		
		if(!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList()).contains(restaurantId)) {
			throw new UnauthorizedModifyingException();
		}
	}

	@Before("execution (* br.com.menu.menudigital.product.ProductController.updateProductImage(..))")
	public void updateProductImage(JoinPoint joinPoint) throws UnauthorizedModifyingException  {
		Object[] args = joinPoint.getArgs();
		Long restaurantId = ((ProductDTO)args[1]).getRestaurantId();
		
		User user = tokenUtils.getUserOnJwsToken();
		
		if(!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList()).contains(restaurantId)) {
			throw new UnauthorizedModifyingException();
		}
	}

	@Before("execution (* br.com.menu.menudigital.product.ProductController.delete(..))")
	public void delete(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();
		Long prodId = (Long)args[0];
		
		Optional<Product> product = productRepository.findById(prodId);
		
		if(product.isPresent()) {
			Long restaurantId = product.get().getRestaurantId();
			
			User user = tokenUtils.getUserOnJwsToken();
			
			if(!user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList()).contains(restaurantId)) {
				throw new UnauthorizedModifyingException();
			}
		}
	}
}
