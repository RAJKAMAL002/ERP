package com.ERP.backend.Service;

import java.util.List;

import com.ERP.backend.Entity.Customer;
import com.ERP.backend.Entity.Sale;


public interface CustomerService {
    // CRUD
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);
    Customer getCustomerById(Long id);
    List<Customer> getAllCustomers();
    void deleteCustomer(Long id);

    // Credit Management
    void addCredit(Long customerId, Double amount);
    void reduceCredit(Long customerId, Double amount);
    Double getCustomerCredit(Long customerId);

    // Reports
    List<Sale> getCustomerSales(Long customerId);
    Double getTotalPurchaseAmount(Long customerId);
}
