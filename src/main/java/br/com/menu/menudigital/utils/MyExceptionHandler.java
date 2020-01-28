package br.com.menu.menudigital.utils;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { UnauthorizedModifyingException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		
		CustomErrorResponse error = new CustomErrorResponse();
		error.setError(ex.getMessage());

		error.setTimestamp(LocalDateTime.now());
		error.setError("Parece que esse restaurante nao e seu.");
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
}