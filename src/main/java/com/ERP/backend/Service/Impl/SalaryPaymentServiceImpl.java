package com.ERP.backend.Service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.SalaryPaymentRequestDTO;
import com.ERP.backend.DTO.SalaryPaymentResponseDTO;
import com.ERP.backend.Entity.Employee;
import com.ERP.backend.Entity.SalaryPayment;
import com.ERP.backend.Repository.EmployeeRepo;
import com.ERP.backend.Repository.SalaryPaymentRepo;
import com.ERP.backend.Service.SalaryPaymentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalaryPaymentServiceImpl implements SalaryPaymentService {

    private final SalaryPaymentRepo salaryPaymentRepo;
    private final EmployeeRepo employeeRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public SalaryPaymentResponseDTO paySalary(SalaryPaymentRequestDTO salaryPayment) {
        Employee employee = employeeRepo.findById(salaryPayment.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        SalaryPayment entity = new SalaryPayment();
        entity.setEmployee(employee);
        entity.setAmount(salaryPayment.getAmount());
        entity.setMonth(salaryPayment.getMonth());
        entity.setPaymentDate(
                salaryPayment.getPaymentDate() == null ? LocalDateTime.now() : salaryPayment.getPaymentDate()
        );

        SalaryPayment saved = salaryPaymentRepo.save(entity);

        SalaryPaymentResponseDTO dto = modelMapper.map(saved, SalaryPaymentResponseDTO.class);
        dto.setEmployeeId(employee.getId());
        dto.setEmployeeName(employee.getName());
        return dto;
    }

    @Override
    public List<SalaryPaymentResponseDTO> getAllSalaryPayments() {
        return salaryPaymentRepo.findAll()
                .stream()
                .map(sp -> {
                    SalaryPaymentResponseDTO dto = modelMapper.map(sp, SalaryPaymentResponseDTO.class);
                    dto.setEmployeeId(sp.getEmployee().getId());
                    dto.setEmployeeName(sp.getEmployee().getName());
                    return dto;
                })
                .toList();
    }

    @Override
    public List<SalaryPaymentResponseDTO> getSalaryPaymentsByMonth(YearMonth month) {
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();
        LocalDateTime startDt = start.atStartOfDay();
        LocalDateTime endDt = end.atTime(23, 59, 59);

        return salaryPaymentRepo.findAll()
                .stream()
                .filter(sp -> {
                    LocalDateTime pd = sp.getPaymentDate();
                    return (pd.isEqual(startDt) || pd.isAfter(startDt)) && (pd.isEqual(endDt) || pd.isBefore(endDt));
                })
                .map(sp -> {
                    SalaryPaymentResponseDTO dto = modelMapper.map(sp, SalaryPaymentResponseDTO.class);
                    dto.setEmployeeId(sp.getEmployee().getId());
                    dto.setEmployeeName(sp.getEmployee().getName());
                    return dto;
                })
                .toList();
    }

    @Override
    public Double getTotalSalaryPaidBetweenDates(LocalDate start, LocalDate end) {
        return salaryPaymentRepo.sumSalaryBetweenDates(
                start.atStartOfDay(),
                end.atTime(23, 59, 59)
        );
    }
}

