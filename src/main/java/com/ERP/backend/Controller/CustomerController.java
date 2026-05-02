package com.ERP.backend.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ERP.backend.DTO.CustomerRequestDTO;
import com.ERP.backend.DTO.CustomerResponseDTO;
import com.ERP.backend.Service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("erp/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO request) {
        CustomerResponseDTO response = customerService.createCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDTO request) {
        CustomerResponseDTO response = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/credit/add")
    public ResponseEntity<Void> addCredit(@PathVariable Long id, @RequestParam Double amount) {
        customerService.addCredit(id, amount);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/credit/reduce")
    public ResponseEntity<Void> reduceCredit(@PathVariable Long id, @RequestParam Double amount) {
        customerService.reduceCredit(id, amount);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/credit")
    public ResponseEntity<Double> getCredit(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerCredit(id));
    }
}

