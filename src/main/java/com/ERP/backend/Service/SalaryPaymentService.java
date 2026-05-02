package com.ERP.backend.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.ERP.backend.DTO.SalaryPaymentRequestDTO;
import com.ERP.backend.DTO.SalaryPaymentResponseDTO;


public interface SalaryPaymentService {
	SalaryPaymentResponseDTO paySalary(SalaryPaymentRequestDTO salaryPayment);

    List<SalaryPaymentResponseDTO> getAllSalaryPayments();

    List<SalaryPaymentResponseDTO> getSalaryPaymentsByMonth(YearMonth month);

    Double getTotalSalaryPaidBetweenDates(LocalDate start, LocalDate end);
}
