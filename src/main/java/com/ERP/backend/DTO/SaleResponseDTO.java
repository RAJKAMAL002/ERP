package com.ERP.backend.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleResponseDTO {

    private Long id;
    private Long customerId;
    private LocalDateTime saleDate;
    private Integer totalAmount;
    private String paymentMode;

    private List<Long> items;
}