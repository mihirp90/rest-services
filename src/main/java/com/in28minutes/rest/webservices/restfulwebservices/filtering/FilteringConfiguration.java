package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringConfiguration {
	
	@GetMapping("/filtering") 
	//only want field1 and field2
	public MappingJacksonValue someBean() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		//filter all fields except for field1 and field2
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		return mapping;
	}
	
	@GetMapping("/filtering-list")
	//only want field3
	public MappingJacksonValue someBeanList() {
		
		List<SomeBean> asList = Arrays.asList(new SomeBean("value1", "value2", "value3"),
				new SomeBean("value11", "value22", "value22"));
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(asList);
		mapping.setFilters(filters);
		return mapping;
	}
}
