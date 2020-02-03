package br.com.menu.menudigital.user;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import br.com.menu.menudigital.utils.TokenUtils;
import br.com.menu.menudigital.utils.UnauthorizedModifyingException;

@Aspect
@Configuration
public class UserAspect {

	private TokenUtils tokenUtils;
	
	public UserAspect(TokenUtils tokenUtils) {
		super();
		this.tokenUtils = tokenUtils;
	}

	@Before("execution(* br.com.menu.menudigital.user.UserController.findUserById(..))")
	public void findUserById(JoinPoint joinPoint) throws UnauthorizedModifyingException {
		Object[] args = joinPoint.getArgs();

		Long userId = ((Long) args[0]);
		User user = tokenUtils.getUserOnJwsToken();

		if (!user.getId().equals(userId)) {
			throw new UnauthorizedModifyingException();
		}
	}
	
}
