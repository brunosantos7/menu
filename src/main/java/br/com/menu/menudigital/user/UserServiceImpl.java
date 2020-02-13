package br.com.menu.menudigital.user;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.menu.menudigital.menu.MenuRepository;
import br.com.menu.menudigital.product.ProductRepository;
import br.com.menu.menudigital.restaurant.Restaurant;
import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurantRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private UserHasRestaurantRepository userHasRestaurantRepository;
	private MenuRepository menuRepository;
	private ProductRepository productRepository;
	
	public UserServiceImpl(UserHasRestaurantRepository userHasRestaurantRepository, MenuRepository menuRepository,
			ProductRepository productRepository) {
		super();
		this.userHasRestaurantRepository = userHasRestaurantRepository;
		this.menuRepository = menuRepository;
		this.productRepository = productRepository;
	}

	@Override
	public boolean hasMaxRestaurantsForPlan(User user) {
		int numberOfRestaurants = userHasRestaurantRepository.countByUserId(user.getId());
		
		return user.getPremiumTypeId().equals(1l) && numberOfRestaurants == 1;
	}

	@Override
	public boolean hasMaxMenusForPlan(User user) {
		
		int numberOfMenus = menuRepository.countByRestaurantIds(user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList()));

		return user.getPremiumTypeId().equals(1l) && numberOfMenus == 1;
	}

	@Override
	public boolean hasMaxProductForPlan(User user) {
		int numberOfProducts = productRepository.countByRestaurantIds(user.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList()));

		return user.getPremiumTypeId().equals(1l) && numberOfProducts == 50;

	}

}
