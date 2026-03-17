package com.ERP.backend.DTO;

import java.time.LocalDateTime;

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
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String category;
    private Double purchasePrice;
    private Double sellingPrice;
    private Integer minStockLevel;
    private Integer maxStockLevel;
    private Integer inventory;
    private LocalDateTime createdAt;
}
