package br.com.menu.menudigital.restaurant;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.menu.menudigital.menu.Menu;
import br.com.menu.menudigital.menu.MenuRepository;
import br.com.menu.menudigital.menu.MenuService;
import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurant;
import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurantRepository;
import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurantService;
import br.com.menu.menudigital.utils.file.FileUtils;

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
	
	public Restaurant saveRestaurant(RestaurantDTO newRestaurantDTO, MultipartFile file, User user) throws IOException {
		Restaurant newRes = restaurantRepository.save(newRestaurantDTO.toRestaurantEntity());

		UserHasRestaurant relationship = new UserHasRestaurant();
		relationship.setUserId(user.getId());
		relationship.setRestaurantId(newRes.getId());

		userHasRestaurantRepository.save(relationship);
		
		if (file != null) {

			String filePath = FileUtils.saveOnDisk(file, newRes.getId());
			
			newRes.setImagePath(filePath);
			return restaurantRepository.save(newRes);
		}

		return newRes;
	}

	@Override
	public Restaurant updateRestaurantImage(MultipartFile file, Restaurant restaurant) throws IOException {
		
		String newPath = null;
		
		if(restaurant.getImagePath() != null) {
			FileUtils.deleteFromDisk(restaurant.getImagePath());
		}
		
		if(file != null) {
			newPath = FileUtils.saveOnDisk(file, restaurant.getId());
		}

		restaurant.setImagePath(newPath);
		return restaurantRepository.save(restaurant);
		
	}
	
}
