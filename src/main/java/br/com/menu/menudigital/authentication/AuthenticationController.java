package br.com.menu.menudigital.authentication;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.user.UserRepository;

@Controller
@RequestMapping("/authenticate")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	private UserRepository userRepository;

	public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
	}

	@PostMapping
	public @ResponseBody String authenticate(@RequestBody AuthenticationRequest authenticationRequest) {

		/// AUTENTICA COM USERNAME
		if (authenticationRequest.getUsername() != null) {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			} catch (BadCredentialsException e) {
				throw new BadCredentialsException("Usuario e senha incorretos!", e);
			}

			User user = userRepository.findByUsername(authenticationRequest.getUsername());

			return generateTokenWithExpirationTime(user.getEmail());
		}

		/// AUTENTICA COM EMAIL
		if (authenticationRequest.getEmail() != null) {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						authenticationRequest.getEmail(), authenticationRequest.getPassword()));
			} catch (BadCredentialsException e) {
				throw new BadCredentialsException("Email e senha incorretos!", e);
			}

			User user = userRepository.findByEmail(authenticationRequest.getEmail());

			return generateTokenWithExpirationTime(user.getEmail());
		}

		throw new BadCredentialsException("Nao foi possivel autenticar!");
	}

	private String generateTokenWithExpirationTime(String userEmail) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		return JWT.create().withClaim("email", userEmail).withIssuedAt(now)
				.withExpiresAt(new Date(nowMillis + TimeUnit.DAYS.toMillis(1))).sign(Algorithm.HMAC256("thesecret"));
	}

}
