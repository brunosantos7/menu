package br.com.menu.menudigital.utils;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.management.BadAttributeValueExpException;
import javax.persistence.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex, error);
		return new ResponseEntity<>(customErrorResponse, customErrorResponse.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		String error = "Erro com os tipos dos parametros";
		CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex, error);
		return new ResponseEntity<>(customErrorResponse, customErrorResponse.getStatus());
	}
	
	@ExceptionHandler(value = { UnauthorizedModifyingException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

		CustomErrorResponse customErrorResponse = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED,
				ex, "Parece que voce nao tem acesso a este recurso.");

		return new ResponseEntity<>(customErrorResponse, customErrorResponse.getStatus());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND);
		customErrorResponse.setMessage(ex.getMessage());
		
		return new ResponseEntity<>(customErrorResponse, customErrorResponse.getStatus());
	}

	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<Object> handleBadCredentials(EntityNotFoundException ex) {
		CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.UNAUTHORIZED);
		customErrorResponse.setMessage(ex.getMessage());
		
		return new ResponseEntity<>(customErrorResponse, customErrorResponse.getStatus());
	}

	@ExceptionHandler({IOException.class, MessagingException.class})
	protected ResponseEntity<Object> handleImagesUpload(EntityNotFoundException ex) {
		CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		customErrorResponse.setMessage(ex.getMessage());
		
		return new ResponseEntity<>(customErrorResponse, customErrorResponse.getStatus());
	}
	
	
	@ExceptionHandler(BadAttributeValueExpException.class)
	protected ResponseEntity<Object> handleBadArgumentsException(EntityNotFoundException ex) {
		CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST);
		customErrorResponse.setMessage(ex.getMessage());
		
		return new ResponseEntity<>(customErrorResponse, customErrorResponse.getStatus());
	}
	
}