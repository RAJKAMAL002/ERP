package com.ERP.backend.Service;

import java.util.List;

import com.ERP.backend.Entity.Employee;
import com.ERP.backend.Entity.SalaryPayment;


public interface EmployeeService {
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee);
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployees();
    void deleteEmployee(Long id);

    List<SalaryPayment> getEmployeeSalaryHistory(Long employeeId);
}
