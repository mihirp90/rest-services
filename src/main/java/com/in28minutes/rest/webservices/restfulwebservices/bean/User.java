package com.in28minutes.rest.webservices.restfulwebservices.bean;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details about the user")
public class User {
		private Integer id;
		
		@Size(min=2, message="Name should have at least 2 characters")
		@NotEmpty(message ="Name can't be null")
		@ApiModelProperty(notes="Name should al least have 2 Characters")
		private String name;
		
		@Past
		@ApiModelProperty(notes="DOB cannot be in future")
		private Date dob;
		
		public User() {
		}
		public User(Integer id, String name, Date dob) {
			super();
			this.id = id;
			this.name = name;
			this.dob = dob;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Date getDob() {
			return dob;
		}
		public void setDob(Date dob) {
			this.dob = dob;
		}
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + ", dob=" + dob + "]";
		}					
}
