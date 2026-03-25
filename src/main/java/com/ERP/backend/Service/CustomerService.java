package com.ERP.backend.Service;

import java.util.List;

import com.ERP.backend.DTO.CustomerRequestDTO;
import com.ERP.backend.DTO.CustomerResponseDTO;
import com.ERP.backend.Entity.Customer;
import com.ERP.backend.Entity.Sale;


public interface CustomerService {
    // CRUD
    CustomerResponseDTO createCustomer(CustomerRequestDTO customer);
    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customer);
    CustomerResponseDTO getCustomerById(Long id);
    List<CustomerResponseDTO> getAllCustomers();
    void deleteCustomer(Long id);

    // Credit Management
    void addCredit(Long customerId, Double amount);
    void reduceCredit(Long customerId, Double amount);
    Double getCustomerCredit(Long customerId);

    // Reports
//    List<Sale> getCustomerSales(Long customerId);
    Double getTotalPurchaseAmount(Long customerId);
}
