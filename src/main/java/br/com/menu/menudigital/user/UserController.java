package br.com.menu.menudigital.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

	private UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	@GetMapping
	public @ResponseBody Iterable<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	@PostMapping
	public @ResponseBody String saveUser() {
		User user = new User();
		user.setPassword("123");
		user.setUserType(1);
		user.setRestaurantId(1);
		user.setUserName("username");
		userRepository.save(user);
		
		return "ok";
	}
}
