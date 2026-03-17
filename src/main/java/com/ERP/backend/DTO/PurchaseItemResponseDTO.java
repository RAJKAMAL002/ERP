package com.ERP.backend.DTO;

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
public class PurchaseItemResponseDTO {
    private Long id;
    private Long purchaseId;
    private Long productId;
    private int quantity;
    private int price;
}
