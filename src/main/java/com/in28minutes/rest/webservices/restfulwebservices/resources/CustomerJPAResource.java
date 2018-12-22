package com.in28minutes.rest.webservices.restfulwebservices.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.bean.Customer;
import com.in28minutes.rest.webservices.restfulwebservices.bean.Post;
import com.in28minutes.rest.webservices.restfulwebservices.bean.User;
import com.in28minutes.rest.webservices.restfulwebservices.daoservice.UserDaoServices;
import com.in28minutes.rest.webservices.restfulwebservices.exceptions.UserAlreadyPresentException;
import com.in28minutes.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.repository.CustomerRepository;
import com.in28minutes.rest.webservices.restfulwebservices.repository.PostRepository;

//HATEOAS - Hyper Media as the engine of application state
@RestController
public class CustomerJPAResource {

	@Autowired
	private UserDaoServices userDaoServices;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	//find all user
	// URI - GET /user
	
	@GetMapping(path="/jpa/customer")
	public Optional<List<Customer>> retrieveAllUsers(){
		return Optional.of(customerRepository.findAll());
	}
	
	@GetMapping(path="/jpa/customerList")
	public List<User> fetchAllUsers(){
		return userDaoServices.findAllUsers();
	}
	
	//find user for particular id
	
	@GetMapping(path="/jpa/_customer/{id}")
	public Optional<Optional<Customer>> retrieveUser(@PathVariable Integer id){
		Optional<Customer> result = customerRepository.findById(id);
		Customer customer = null;
		if(result.isPresent()){
			customer = result.get();
		}
		if(customer == null) {
			throw new UserNotFoundException("id-" + id);
		}
		return Optional.of(result);
	}
	
	@GetMapping(path="/jpa/customer/{id}")
	public Resource<Customer> retrieveUserWithLink(@PathVariable Integer id){
		Optional<Customer> customer= customerRepository.findById(id);
		
		if(!customer.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		
		//HATEOAS to get link to all users present
		Resource<Customer> resource = new Resource<>(customer.get());
		//getting uri link to retrieveAllUsers method
		
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).fetchAllUsers());
		resource.add(linkTo.withRel("all-users"));

		return resource;
	}
	
	//adding new user in list
	
	@PostMapping(path="/jpa/customer")
	public Optional<ResponseEntity<Object>> createUser(@Valid @RequestBody Customer customer){
		Customer savedCustomer = null;
		try {
			savedCustomer = customerRepository.save(customer);
		}
		catch(UserAlreadyPresentException ex) {
			throw ex;
		}
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedCustomer.getId())
			.toUri();
		 
		return Optional.of(ResponseEntity.created(location).build());
	}
	
	//delete the user 
	@DeleteMapping(path="/jpa/customer/{id}")
	public void deleteUser(@PathVariable Integer id){
		customerRepository.deleteById(id);
	}
	
	@GetMapping(path="/jpa/customer/{id}/posts")
	public Optional<List<Post>> retrieveAllPosts(@PathVariable int id){
		Optional<Customer> customer = customerRepository.findById(id);
		if(!customer.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		return Optional.of(customer.get().getPosts());
	}
	
	@PostMapping(path="/jpa/customer/{id}/posts")
	public Optional<ResponseEntity<Object>> createPost(@PathVariable int id, @RequestBody Post post){
		Optional<Customer> customerOpt = customerRepository.findById(id);
		
		if(!customerOpt.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		
		Customer customer = customerOpt.get();
		post.setCustomer(customer);
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(post.getId())
				.toUri();
			 
		return Optional.of(ResponseEntity.created(location).build());
		
	}
	
}
