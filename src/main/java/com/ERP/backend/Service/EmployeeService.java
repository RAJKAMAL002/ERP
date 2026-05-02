package com.ERP.backend.Service;

import java.util.List;

import com.ERP.backend.DTO.EmployeeRequestDTO;
import com.ERP.backend.DTO.EmployeeResponseDTO;
import com.ERP.backend.DTO.SalaryPaymentResponseDTO;


public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employee);
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employee);
    EmployeeResponseDTO getEmployeeById(Long id);
    List<EmployeeResponseDTO> getAllEmployees();
    void deleteEmployee(Long id);

    List<SalaryPaymentResponseDTO> getEmployeeSalaryHistory(Long employeeId);
}
