package br.com.menu.menudigital.restaurant;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.menu.menudigital.menu.Menu;
import br.com.menu.menudigital.menu.MenuRepository;
import br.com.menu.menudigital.menu.MenuService;
import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurant;
import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurantRepository;
import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	private RestaurantRepository restaurantRepository;
	private UserHasRestaurantService userHasRestaurantService;
	private UserHasRestaurantRepository userHasRestaurantRepository;
	private MenuService menuService;
	private MenuRepository menuRepository;
	
	public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
			UserHasRestaurantService userHasRestaurantService, UserHasRestaurantRepository userHasRestaurantRepository,
			MenuService menuService, MenuRepository menuRepository) {
		super();
		this.restaurantRepository = restaurantRepository;
		this.userHasRestaurantService = userHasRestaurantService;
		this.userHasRestaurantRepository = userHasRestaurantRepository;
		this.menuService = menuService;
		this.menuRepository = menuRepository;
	}

	@Override
	public boolean softDeleteRestaurant(Restaurant restaurant) {

		Long restaurantId = restaurant.getId();
		
		List<UserHasRestaurant> userHasRestaurantRelationShip = userHasRestaurantRepository.findByRestaurantId(restaurantId);
		
		for (UserHasRestaurant userHasRestaurant : userHasRestaurantRelationShip) {
			userHasRestaurantService.softDeleteRelationship(userHasRestaurant);
		}
		
		List<Menu> menus = menuRepository.findByRestaurantId(restaurantId);
		for (Menu menu : menus) {
			menuService.softDeleteMenu(menu);
		}
		
		restaurant.setDeleted(true);
		restaurantRepository.save(restaurant);
		
		return true;
	}

	
}
