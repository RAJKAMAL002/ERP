package com.ERP.backend.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.ERP.backend.Entity.SalaryPayment;


public interface SalaryPaymentService {
	SalaryPayment paySalary(SalaryPayment salaryPayment);

    List<SalaryPayment> getAllSalaryPayments();

    List<SalaryPayment> getSalaryPaymentsByMonth(YearMonth month);

    Double getTotalSalaryPaidBetweenDates(LocalDate start, LocalDate end);
}
