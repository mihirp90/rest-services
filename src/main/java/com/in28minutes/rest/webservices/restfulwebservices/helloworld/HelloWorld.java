package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method=RequestMethod.GET, path="/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path="/hello-earth")
	public HelloEarth helloEarth() {
		return new HelloEarth("Hello Earth");
	}
	
	@GetMapping(path="/hello-earth/pathVariable/{name}")
	public HelloEarth helloEarthPathVariable(@PathVariable String name) {
		return new HelloEarth(String.format("Hello Earth %s", name));
	}
	
	@GetMapping(path="/hello-world-i18n")
	public String helloWorldI18N(/*@RequestHeader(name="Accept-Language", required=false ) Locale locale*/) {
		//return messageSource.getMessage("good.morning.messsage", null, locale);
		return messageSource.getMessage("good.morning.messsage", null, LocaleContextHolder.getLocale());
	}
	

}
