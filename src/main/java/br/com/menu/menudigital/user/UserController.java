package br.com.menu.menudigital.user;

import javax.management.BadAttributeValueExpException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
