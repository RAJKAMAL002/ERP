package com.ERP.backend.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryPaymentResponseDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private int amount;
    private String month;
    private LocalDateTime paymentDate;
    private LocalDateTime createdAt;
}

