package br.com.menu.menudigital.restaurant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.activity.InvalidActivityException;

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
import br.com.menu.menudigital.restaurantprofile.RestaurantProfile;
import br.com.menu.menudigital.restaurantprofile.RestaurantProfileRepository;
import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.user.UserRepository;
import javassist.NotFoundException;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

	private RestaurantRepository restaurantRepository;

	private RestaurantProfileRepository restaurantProfileRepository;

	private UserRepository userRepository;
	
	public RestaurantController(RestaurantRepository restaurantRepository,
			RestaurantProfileRepository restaurantProfileRepository, UserRepository userRepository) {
		super();
		this.restaurantRepository = restaurantRepository;
		this.restaurantProfileRepository = restaurantProfileRepository;
		this.userRepository = userRepository;
	}

	@GetMapping
	public @ResponseBody Iterable<Restaurant> getAllRestaurants() {
		return restaurantRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Restaurant getRestaurantById(@PathVariable Long id) throws NotFoundException {
		return restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Does not exist restaurant with this id"));
	}
	
	@GetMapping("/{id}/profiles")
	public @ResponseBody List<RestaurantProfile> getRestaurantProfileByRestaurantId(@PathVariable Long id) {
		return restaurantProfileRepository.findByRestaurantId(id);
	}
	
	@PostMapping
	public @ResponseBody Restaurant save(RestaurantDTO newRestaurantDTO, @RequestParam(name="file", required=false) MultipartFile file, Principal principal) throws IOException {
		User user = userRepository.findByUsername(principal.getName());
		
		if(user.getRestaurantId() != null) {
			throw new InvalidActivityException("Nao e possivel cadastrar mais de um restaurante por usuario.");
		}
		
		Restaurant newRes = restaurantRepository.save(newRestaurantDTO.toRestaurantEntity());
		
		user.setRestaurantId(newRes.getId());
		userRepository.save(user);
		
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
	public @ResponseBody Restaurant updateRestaurant(@PathVariable Long id, RestaurantDTO newRestaurantDTO, @RequestParam(name="file", required=false) MultipartFile file, Principal principal) throws NotFoundException, IOException {
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Does not exist restaurant with this id."));
		
		restaurant.setName(newRestaurantDTO.getName());
		Restaurant updatedRest = restaurantRepository.save(restaurant);

		Path path = Paths.get(String.format("images/restaurant/%s", updatedRest.getId()));
		
		if(file != null) {
			
			if(path.toFile().exists()) {
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
	public @ResponseBody void deleteRestaurant(@PathVariable("id") Long restaurantId) throws NotFoundException {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Does not exist restaurant with this id."));
		Path path = Paths.get(String.format("images/restaurant/%s", restaurant.getId()));
		FileSystemUtils.deleteRecursively(path.toFile());
		
		Optional<User> userOwner = userRepository.findByRestaurantId(restaurant.getId());
		if(userOwner.isPresent()) {
			User user = userOwner.get();
			user.setRestaurantId(null);
			userRepository.save(user);
		}
		
		restaurantRepository.delete(restaurant);

	}
}
