package br.com.menu.menudigital.user;

import java.util.List;
import java.util.Optional;

import javax.management.BadAttributeValueExpException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.menu.menudigital.restaurant.Restaurant;
import javassist.NotFoundException;

@Controller
@RequestMapping("/user")
public class UserController {

	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;

	public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping
	public @ResponseBody User saveUser(@RequestBody UserDTO userDto) throws BadAttributeValueExpException {
		User user = userRepository.findByUsername(userDto.getUsername());
		
		if(user != null) {
			throw new BadAttributeValueExpException("Ja existe um usuario com este username.");
		}
		
		String encoded = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(encoded);
		
		return userRepository.save(userDto.toEntity());
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Optional<User> findUserById(@PathVariable Long id) {
		return userRepository.findById(id);
	}
	
	@GetMapping("/{id}/restaurants")
	public @ResponseBody List<Restaurant> findRestaurantsByUserId(@PathVariable Long id) throws NotFoundException {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Does not exist menu with this id."));
		return user.getRestaurants();
	}
}
