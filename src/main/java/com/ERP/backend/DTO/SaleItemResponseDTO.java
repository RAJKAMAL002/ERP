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
public class SaleItemResponseDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private int price;
    private int total;
}
