package br.com.menu.menudigital.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

	private RestaurantRepository restaurantRepository;

	public RestaurantController(RestaurantRepository restaurantRepository) {
		super();
		this.restaurantRepository = restaurantRepository;
	}
	
	@GetMapping
	public @ResponseBody Iterable<Restaurant> getAllRestaurants() {
		return restaurantRepository.findAll();
	}
	
	@PostMapping("/save")
	public @ResponseBody String save() {
		Restaurant rest = new Restaurant();
		rest.setName("test");
		restaurantRepository.save(rest);
		
		return "ok";
	}
}
