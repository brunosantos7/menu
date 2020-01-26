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

@Controller
@RequestMapping("/authenticate")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping
	public @ResponseBody String authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Usuario e senha incorretos!", e);
		}
		long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	    			
		return JWT.create().withClaim("username", authenticationRequest.getUsername()).withIssuedAt(now).withExpiresAt(new Date(nowMillis + TimeUnit.DAYS.toMillis(1))).sign(Algorithm.HMAC256("thesecret"));
	}
	
}
