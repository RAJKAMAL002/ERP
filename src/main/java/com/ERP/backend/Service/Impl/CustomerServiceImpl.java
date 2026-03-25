package com.ERP.backend.Service.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.CustomerRequestDTO;
import com.ERP.backend.DTO.CustomerResponseDTO;
import com.ERP.backend.Entity.Customer;
import com.ERP.backend.Repository.CustomerRepo;
import com.ERP.backend.Service.CustomerService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
	
	private final CustomerRepo customerRepo;
	private final ModelMapper modelMapper;

	public CustomerResponseDTO createCustomer(CustomerRequestDTO customer) {

	    if (customerRepo.existsByEmail(customer.getEmail())) 
	    	throw new IllegalArgumentException("Customer with email already exists");
	    if (customerRepo.existsByPhone(customer.getPhone())) 
	    	throw new IllegalArgumentException("Customer with phone already exists");

	    Customer customerEntity = modelMapper.map(customer, Customer.class);
	    Customer savedCustomer = customerRepo.save(customerEntity);

	    return modelMapper.map(savedCustomer, CustomerResponseDTO.class);
	}

	@Override
	@Transactional
	public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customer) {
		Customer getOrgCustomer = customerRepo.findById(id).orElseThrow();
		
		if(!customer.getEmail().equals(getOrgCustomer.getEmail())) {
			if (customerRepo.existsByEmail(customer.getEmail())) 
				throw new IllegalArgumentException("Customer with email already exists");
		}
		if(!customer.getPhone().equals(getOrgCustomer.getPhone())) {
		    if (customerRepo.existsByPhone(customer.getPhone())) 
		    	throw new IllegalArgumentException("Customer with phone already exists");
		}
		
		getOrgCustomer.setName(customer.getName());
		getOrgCustomer.setPhone(customer.getPhone());
		getOrgCustomer.setEmail(customer.getEmail());
		getOrgCustomer.setAddress(customer.getAddress());
		
		return modelMapper.map(getOrgCustomer, CustomerResponseDTO.class);
	}

	@Override
	public CustomerResponseDTO getCustomerById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerResponseDTO> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCustomer(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCredit(Long customerId, Double amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reduceCredit(Long customerId, Double amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Double getCustomerCredit(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getTotalPurchaseAmount(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
