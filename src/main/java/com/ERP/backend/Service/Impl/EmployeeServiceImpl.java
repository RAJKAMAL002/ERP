package com.ERP.backend.Service.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.EmployeeRequestDTO;
import com.ERP.backend.DTO.EmployeeResponseDTO;
import com.ERP.backend.DTO.SalaryPaymentResponseDTO;
import com.ERP.backend.Entity.Employee;
import com.ERP.backend.Repository.EmployeeRepo;
import com.ERP.backend.Repository.SalaryPaymentRepo;
import com.ERP.backend.Service.EmployeeService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final SalaryPaymentRepo salaryPaymentRepo;
    private final ModelMapper modelMapper;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employee) {
        Employee entity = modelMapper.map(employee, Employee.class);
        Employee saved = employeeRepo.save(entity);
        return modelMapper.map(saved, EmployeeResponseDTO.class);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employee) {
        Employee existing = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        existing.setName(employee.getName());
        existing.setRole(employee.getRole());
        existing.setSalary(employee.getSalary());
        existing.setJoiningDate(employee.getJoiningDate());
        return modelMapper.map(existing, EmployeeResponseDTO.class);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepo.findAll()
                .stream()
                .map(e -> modelMapper.map(e, EmployeeResponseDTO.class))
                .toList();
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepo.delete(employee);
    }

    @Override
    public List<SalaryPaymentResponseDTO> getEmployeeSalaryHistory(Long employeeId) {
        employeeRepo.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        return salaryPaymentRepo.findByEmployeeIdOrderByPaymentDateDesc(employeeId)
                .stream()
                .map(sp -> {
                    SalaryPaymentResponseDTO dto = modelMapper.map(sp, SalaryPaymentResponseDTO.class);
                    dto.setEmployeeId(sp.getEmployee().getId());
                    dto.setEmployeeName(sp.getEmployee().getName());
                    return dto;
                })
                .toList();
    }
}

