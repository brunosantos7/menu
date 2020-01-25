package br.com.menu.menudigital.restaurant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

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
import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.user.UserRepository;
import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurant;
import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurantRepository;
import javassist.NotFoundException;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

	private RestaurantRepository restaurantRepository;

	private UserRepository userRepository;
	
	private UserHasRestaurantRepository userHasRestaurantRepository;
	
	private MenuRepository menuRepository;
	
	public RestaurantController(RestaurantRepository restaurantRepository, UserRepository userRepository,
			UserHasRestaurantRepository userHasRestaurantRepository, MenuRepository menuRepository) {
		super();
		this.restaurantRepository = restaurantRepository;
		this.userRepository = userRepository;
		this.userHasRestaurantRepository = userHasRestaurantRepository;
		this.menuRepository = menuRepository;
	}

	@GetMapping
	public @ResponseBody Iterable<Restaurant> getAllRestaurants() {
		return restaurantRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Restaurant getRestaurantById(@PathVariable Long id) throws NotFoundException {
		return restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Does not exist restaurant with this id"));
	}
	
	@GetMapping("/{id}/menus")
	public @ResponseBody List<Menu> getRestaurantMenus(@PathVariable Long id) {
		return menuRepository.findByRestaurantId(id);
	}
	
	@PostMapping
	public @ResponseBody Restaurant save(@Valid RestaurantDTO newRestaurantDTO, @RequestParam(name="file", required=false) MultipartFile file, Principal principal) throws IOException {
		User user = userRepository.findByUsername(principal.getName());
		
		Restaurant newRes = restaurantRepository.save(newRestaurantDTO.toRestaurantEntity());
		
		UserHasRestaurant relationship = new UserHasRestaurant();
		relationship.setUserId(user.getId());
		relationship.setRestaurantId(newRes.getId());
		
		userHasRestaurantRepository.save(relationship);
		
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
	public @ResponseBody Restaurant updateRestaurant(@PathVariable Long id, @Valid RestaurantDTO newRestaurantDTO, @RequestParam(name="file", required=false) MultipartFile file, Principal principal) throws NotFoundException, IOException {
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Does not exist restaurant with this id."));
		
		Restaurant restaurantEntity = newRestaurantDTO.toRestaurantEntity();
		restaurantEntity.setImagePath(restaurant.getImagePath());
		restaurantEntity.setId(restaurant.getId());
		
		return restaurantRepository.save(restaurantEntity);
	}
	
	@PutMapping("/{id}/image")
	public @ResponseBody Restaurant updateRestaurantImage(@PathVariable Long id, @RequestParam(name="file", required=false) MultipartFile file, Principal principal) throws NotFoundException, IOException {
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Does not exist restaurant with this id."));
		
		Path path = Paths.get(String.format("images/restaurant/%s", restaurant.getId()));
		
		if(file != null) {
			
			if(path.toFile().exists()) {
		        FileSystemUtils.deleteRecursively(path.toFile());
			}
			
	        Files.createDirectories(path);
	        
	        String filename = StringUtils.cleanPath(file.getOriginalFilename());
	        
	        Files.copy(file.getInputStream(), path.resolve(filename));
	        restaurant.setImagePath(path.resolve(filename).toString());

		} else {
	        FileSystemUtils.deleteRecursively(path.toFile());
	        restaurant.setImagePath(null);
		}
		
		return restaurantRepository.save(restaurant);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteRestaurant(@PathVariable("id") Long restaurantId) throws NotFoundException {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Does not exist restaurant with this id."));
		
		Path path = Paths.get(String.format("images/restaurant/%s", restaurant.getId()));
		FileSystemUtils.deleteRecursively(path.toFile());
		
		List<UserHasRestaurant> listRelationship = userHasRestaurantRepository.findByRestaurantId(restaurant.getId());
		userHasRestaurantRepository.deleteAll(listRelationship);
		
		restaurantRepository.delete(restaurant);
	}
}
