package br.com.menu.menudigital.utils;

abstract class ApiSubError {

}

public class CustomSubErrorResponse extends ApiSubError {
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;

	CustomSubErrorResponse(String object, String message) {
		this.object = object;
		this.message = message;
	}
}