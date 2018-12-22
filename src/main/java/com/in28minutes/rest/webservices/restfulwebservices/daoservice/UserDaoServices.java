package com.in28minutes.rest.webservices.restfulwebservices.daoservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.in28minutes.rest.webservices.restfulwebservices.bean.User;
import com.in28minutes.rest.webservices.restfulwebservices.exceptions.UserAlreadyPresentException;

@Component
public class UserDaoServices {

	private static List<User> users = new ArrayList<>();

	static {
		users.add(new User(1, "Mihir",new Date()));
		users.add(new User(2, "Mayur",new Date()));
		users.add(new User(3, "Awadh",new Date()));
		users.add(new User(4, "Amar",new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(users.size()+1);
		}
		else if(users.stream()
				.filter(existUser ->existUser.getId() == user.getId()) != null) {
			throw new UserAlreadyPresentException("user - " + user);
		}
		
		users.add(user);
		return user;
	}
	
	public Optional<User> findOne(Integer id) {
		return users.stream().filter(user -> user.getId() == id).findFirst();
	}
	
	public User findUser(Integer id) {
		for(User user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public Optional<User> deleteUser(Integer id) {
		/*return Optional.of(users.stream().filter(user -> user.getId() == id)
				.collect(Collectors.toList()));*/
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId() == id) {
				iterator.remove();
				return Optional.of(user);
			}
		}
		return Optional.empty();
	}

	public List<User> findAllUsers() {
		return users;
	}
}
