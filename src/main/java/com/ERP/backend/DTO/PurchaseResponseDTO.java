package com.ERP.backend.DTO;

import java.time.LocalDateTime;
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
public class PurchaseResponseDTO {
	 private Long id;
	 private String supplierName;
	 private LocalDateTime purchaseDate;
	 private Double totalAmount;
	 private List<Long> items;
}
