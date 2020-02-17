package br.com.menu.menudigital.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.BadAttributeValueExpException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.menu.menudigital.restaurant.Restaurant;
import br.com.menu.menudigital.utils.exception.ResourceAlreadyExistsException;

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
	public @ResponseBody User saveUser(@RequestBody @Valid UserDTO userDto) throws ResourceAlreadyExistsException {
		User user = userRepository.findByEmail(userDto.getEmail());
		
		if(user != null) {
			throw new ResourceAlreadyExistsException("Ja existe um usuario com este email.");
		}
		
		if(userDto.getUsername() != null) {
			user = userRepository.findByUsername(userDto.getUsername());
			
			if(user != null) {
				throw new ResourceAlreadyExistsException("Ja existe um usuario com este username.");
			}
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
	public @ResponseBody List<Restaurant> findRestaurantsByUserId(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe usuario com este id."));
		return user.getRestaurants().stream().filter(restaurant -> !restaurant.isDeleted()).collect(Collectors.toList());
	}
}
