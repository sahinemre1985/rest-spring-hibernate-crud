package com.emre.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emre.springdemo.entity.Customer;
import com.emre.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	//inject customer service
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		
		return customerService.getCustomers();
	}	
	
	@GetMapping("/customers/{customerid}")
	public Customer getCustomers(@PathVariable int customerid){
		
		
		Customer theCustomer = customerService.getCustomer(customerid);  
		
		//check if customer exists, if not return 404
		if(theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerid);
		}
		
		return theCustomer;
		
	}	
	
	
	@PostMapping("/customers")
	public Customer saveCustomer(@RequestBody Customer theCustomer){
		
		// save the customer using our service
		
		//overwrite json id for guarantee new insert. 0 or null means this customers is new so do not update with the existing one
		theCustomer.setId(0); 
		
		customerService.saveCustomer(theCustomer);	
		
		return theCustomer;
	}
	
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer){
		
		// save the customer using our service

		customerService.saveCustomer(theCustomer);	
		
		return theCustomer;
	}
	
	@DeleteMapping("/customers/{customerid}")
	public String deleteCustomer(@PathVariable int customerid) {
		
		Customer theCustomer = customerService.getCustomer(customerid);  
		
		//check if customer exists, if not return 404
		if(theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerid);
		}
		
		customerService.deleteCustomer(customerid);
		
		return "Deleted Customer id - " + customerid;
	}
}
