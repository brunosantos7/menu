package br.com.menu.menudigital.restaurant;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.menu.menudigital.menu.Menu;
import br.com.menu.menudigital.menu.MenuRepository;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

	private RestaurantRepository restaurantRepository;
	private MenuRepository menuRepository;

	public RestaurantController(RestaurantRepository restaurantRepository, MenuRepository menuRepository) {
		super();
		this.restaurantRepository = restaurantRepository;
		this.menuRepository = menuRepository;
	}

	@GetMapping
	public @ResponseBody Iterable<Restaurant> getAllRestaurants() {
		return restaurantRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Optional<Restaurant> getRestaurantById(@PathVariable Long id) throws Exception {
		return restaurantRepository.findById(id);
	}
	
	@GetMapping("/{id}/menus")
	public @ResponseBody List<Menu> getResrtaurantMenus(@PathVariable Long id) {
		return menuRepository.findByRestaurantId(id);
	}
	
	@PostMapping("/save")
	public @ResponseBody Restaurant save(@RequestBody RestaurantDTO newRestaurantDTO) {
		return restaurantRepository.save(newRestaurantDTO.toRestaurantEntity());
		
	}
	
	@PutMapping("/{id}")
	public @ResponseBody Restaurant updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDTO newRestaurantDTO) throws Exception {
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new Exception("Does not exist restaurant with this id."));
		
		restaurant.setName(newRestaurantDTO.getName());
		
		return restaurantRepository.save(restaurant);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteRestaurant(@PathVariable("id") Long restaurantId) throws Exception {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new Exception("Does not exist restaurant with this id."));
		restaurantRepository.delete(restaurant);
	}
}
