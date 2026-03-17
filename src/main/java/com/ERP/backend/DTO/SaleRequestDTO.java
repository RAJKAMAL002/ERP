package com.ERP.backend.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleRequestDTO {
    private Long customerId;
    private String paymentMode; // CASH / UPI / CARD
    private Integer amountPaid;
    private List<SaleItemRequestDTO> items;
}
