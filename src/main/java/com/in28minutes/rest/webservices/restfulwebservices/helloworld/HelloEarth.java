package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

public class HelloEarth  {

	String message;
	public HelloEarth(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "HelloEarth [message=" + message + "]";
	}
	
}
