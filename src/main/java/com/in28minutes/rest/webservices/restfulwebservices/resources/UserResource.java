package com.in28minutes.rest.webservices.restfulwebservices.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.bean.User;
import com.in28minutes.rest.webservices.restfulwebservices.daoservice.UserDaoServices;
import com.in28minutes.rest.webservices.restfulwebservices.exceptions.UserAlreadyPresentException;
import com.in28minutes.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;

//HATEOAS - Hyper Media as the engine of application state
@RestController
public class UserResource {

	@Autowired
	private UserDaoServices userDaoServices;
	
	//find all user
	// URI - GET /user
	
	@GetMapping(path="/users")
	public Optional<List<User>> retrieveAllUsers(){
		return Optional.of(userDaoServices.findAll());
	}
	
	@GetMapping(path="/usersList")
	public List<User> fetchAllUsers(){
		return userDaoServices.findAllUsers();
	}
	
	//find user for particular id
	
	@GetMapping(path="/users/{id}")
	public Optional<Optional<User>> retrieveUser(@PathVariable Integer id){
		Optional<User> result = userDaoServices.findOne(id);
		User user = null;
		if(result.isPresent()){
			user = result.get();
		}
		if(user == null) {
			throw new UserNotFoundException("id-" + id);
		}
		return Optional.of(result);
	}
	
	@GetMapping(path="/user/{id}")
	public Resource<User> retrieveUserWithLink(@PathVariable Integer id){
		User user= userDaoServices.findUser(id);
		
		if(user == null) {
			throw new UserNotFoundException("id-" + id);
		}
		
		//HATEOAS to get link to all users present
		Resource<User> resource = new Resource<>(user);
		//getting uri link to retrieveAllUsers method
		
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).fetchAllUsers());
		resource.add(linkTo.withRel("all-users"));

		return resource;
	}
	
	//adding new user in list
	
	@PostMapping(path="/users")
	public Optional<ResponseEntity<Object>> createUser(@Valid @RequestBody User user){
		User savedUser = null;
		try {
			savedUser = userDaoServices.save(user);
		}
		catch(UserAlreadyPresentException ex) {
			throw ex;
		}
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
		 
		return Optional.of(ResponseEntity.created(location).build());
	}
	
	//delete the user 
	@DeleteMapping(path="/users/{id}")
	public void deleteUser(@PathVariable Integer id){
		Optional<User> result = userDaoServices.deleteUser(id);
		User user = null;
		if(result.isPresent()) {
			user = result.get();
		}
		if(user == null) {
			throw new UserNotFoundException("id-" + id);
		}
	}
}
