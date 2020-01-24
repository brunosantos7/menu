package br.com.menu.menudigital.restaurantprofile;

import java.util.List;

import org.springframework.stereotype.Controller;
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
@RequestMapping("/restaurantProfile")
public class RestaurantProfileController {

	private RestaurantProfileRepository restaurantProfileRepository;
	
	private MenuRepository menuRepository;
	
	public RestaurantProfileController(RestaurantProfileRepository restaurantProfileRepository,
			MenuRepository menuRepository) {
		super();
		this.restaurantProfileRepository = restaurantProfileRepository;
		this.menuRepository = menuRepository;
	}

	@GetMapping("/{id}/menus")
	public @ResponseBody List<Menu> getRestaurantMenus(@PathVariable Long id) {
		return menuRepository.findByRestaurantProfileId(id);
	}

	@PostMapping
	public @ResponseBody RestaurantProfile save(@RequestBody RestaurantProfileDTO restaurantProfileDTO) {
		return restaurantProfileRepository.save(restaurantProfileDTO.toEntity());
	}
	
	@PutMapping("/{id}")
	public @ResponseBody RestaurantProfile update(@RequestBody RestaurantProfileDTO restaurantProfileDTO, @PathVariable Long id) {
		
		RestaurantProfile entity = restaurantProfileDTO.toEntity();
		entity.setId(id);
		return restaurantProfileRepository.save(entity);
	}
}
