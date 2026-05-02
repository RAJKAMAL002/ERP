package com.ERP.backend.Controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ERP.backend.DTO.SalaryPaymentRequestDTO;
import com.ERP.backend.DTO.SalaryPaymentResponseDTO;
import com.ERP.backend.Service.SalaryPaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("erp/api/salary-payments")
@RequiredArgsConstructor
public class SalaryPaymentController {

    private final SalaryPaymentService salaryPaymentService;

    @PostMapping
    public ResponseEntity<SalaryPaymentResponseDTO> paySalary(@RequestBody SalaryPaymentRequestDTO request) {
        SalaryPaymentResponseDTO response = salaryPaymentService.paySalary(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SalaryPaymentResponseDTO>> getAllSalaryPayments() {
        return ResponseEntity.ok(salaryPaymentService.getAllSalaryPayments());
    }

    @GetMapping("/month")
    public ResponseEntity<List<SalaryPaymentResponseDTO>> getSalaryPaymentsByMonth(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        return ResponseEntity.ok(salaryPaymentService.getSalaryPaymentsByMonth(month));
    }

    @GetMapping("/total/range")
    public ResponseEntity<Double> getTotalSalaryPaidBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(salaryPaymentService.getTotalSalaryPaidBetweenDates(start, end));
    }
}

