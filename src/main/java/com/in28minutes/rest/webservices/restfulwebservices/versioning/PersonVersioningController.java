package com.in28minutes.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {
	
	@GetMapping("v1/person")
	public Person getPerson() {
		return new Person("Mihir");
	}
	
	@GetMapping("v2/person")
	public PersonV1 getPersonV1() {
		return new PersonV1(new Name("Mihir", "Prakash"));
	}
	
	@GetMapping(value="/person/header", headers="X-API-VERSION=2")
	public Person getPersonHeaderV1() {
		return new Person("Mihir");
	}
	
	@GetMapping(value="/person/header", headers="X-API-VERSION=1")
	public PersonV1 getPersonHeadderrV2() {
		return new PersonV1(new Name("Mihir", "Prakash"));
	}
}
