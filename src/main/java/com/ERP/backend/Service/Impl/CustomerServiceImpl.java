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
		Customer customer = customerRepo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
		return modelMapper.map(customer, CustomerResponseDTO.class);
	}

	@Override
	public List<CustomerResponseDTO> getAllCustomers() {
		return customerRepo.findAll()
				.stream()
				.map(c -> modelMapper.map(c, CustomerResponseDTO.class))
				.toList();
	}

	@Override
	public void deleteCustomer(Long id) {
		Customer customer = customerRepo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
		customerRepo.delete(customer);
	}

	@Override
	@Transactional
	public void addCredit(Long customerId, Double amount) {
		if (amount == null || amount <= 0) throw new IllegalArgumentException("Amount must be greater than 0");
		Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
		customer.setCreditBalance(customer.getCreditBalance() + amount.intValue());
	}

	@Override
	@Transactional
	public void reduceCredit(Long customerId, Double amount) {
		if (amount == null || amount <= 0) throw new IllegalArgumentException("Amount must be greater than 0");
		Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
		int updated = customer.getCreditBalance() - amount.intValue();
		if (updated < 0) throw new IllegalArgumentException("Credit balance cannot be less than zero");
		customer.setCreditBalance(updated);
	}

	@Override
	public Double getCustomerCredit(Long customerId) {
		Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
		return (double) customer.getCreditBalance();
	}

	@Override
	public Double getTotalPurchaseAmount(Long customerId) {
		customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
		return 0.0;
	}

}
