package com.in28minutes.rest.webservices.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservices.restfulwebservices.bean.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
