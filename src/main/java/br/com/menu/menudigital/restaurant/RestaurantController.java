package br.com.menu.menudigital.restaurant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	
	@PostMapping
	public @ResponseBody Restaurant save(RestaurantDTO newRestaurantDTO, @RequestParam(name="file", required=false) MultipartFile file) throws IOException {

		Restaurant newRes = restaurantRepository.save(newRestaurantDTO.toRestaurantEntity());
		
		if(file != null) {
			Path path = Paths.get(String.format("images/restaurant/%s", newRes.getId()));
			
	        Files.createDirectories(path);
	        
	        String filename = StringUtils.cleanPath(file.getOriginalFilename());
	        
	        Files.copy(file.getInputStream(), path.resolve(filename));
	        newRes.setImagePath(path.resolve(filename).toString());

	        return restaurantRepository.save(newRes);
		}
		
        return newRes;
	}
	
	@PutMapping("/{id}")
	public @ResponseBody Restaurant updateRestaurant(@PathVariable Long id, RestaurantDTO newRestaurantDTO, @RequestParam(name="file", required=false) MultipartFile file) throws Exception {
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new Exception("Does not exist restaurant with this id."));
		
		restaurant.setName(newRestaurantDTO.getName());
		Restaurant updatedRest = restaurantRepository.save(restaurant);

		Path path = Paths.get(String.format("images/restaurant/%s", updatedRest.getId()));
		
		if(file != null) {
			
			if(Files.exists(path)) {
		        FileSystemUtils.deleteRecursively(path.toFile());
			}
			
	        Files.createDirectories(path);
	        
	        String filename = StringUtils.cleanPath(file.getOriginalFilename());
	        
	        Files.copy(file.getInputStream(), path.resolve(filename));
	        updatedRest.setImagePath(path.resolve(filename).toString());

		} else {
	        FileSystemUtils.deleteRecursively(path.toFile());
	        restaurant.setImagePath(null);
		}
		
		return restaurantRepository.save(updatedRest);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteRestaurant(@PathVariable("id") Long restaurantId) throws Exception {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new Exception("Does not exist restaurant with this id."));
		restaurantRepository.delete(restaurant);
	}
}
