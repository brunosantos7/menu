package br.com.menu.menudigital.restaurant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.menu.menudigital.menu.Menu;
import br.com.menu.menudigital.menu.MenuRepository;
import br.com.menu.menudigital.menu.MenuService;
import br.com.menu.menudigital.user.User;
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
	
	public Restaurant saveRestaurant(RestaurantDTO newRestaurantDTO, MultipartFile file, User user) throws IOException {
		Restaurant newRes = restaurantRepository.save(newRestaurantDTO.toRestaurantEntity());

		UserHasRestaurant relationship = new UserHasRestaurant();
		relationship.setUserId(user.getId());
		relationship.setRestaurantId(newRes.getId());

		userHasRestaurantRepository.save(relationship);

		if (file != null) {
			Path path = Paths.get(String.format("images/restaurant/%s", newRes.getId()));

			try {
				Files.createDirectories(path);

				String filename = StringUtils.cleanPath(file.getOriginalFilename());

				Files.copy(file.getInputStream(), path.resolve(filename));
				newRes.setImagePath(path.resolve(filename).toString());

			} catch (IOException e) {
				throw new IOException("Erro ao salvar imagem no disco.", e);
			}

			return restaurantRepository.save(newRes);
		}

		return newRes;
	}
	
}
