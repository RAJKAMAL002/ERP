package com.ERP.backend.mapper;

import java.util.Collections;
import java.util.Objects;

import com.ERP.backend.DTO.PurchaseResponseDTO;
import com.ERP.backend.Entity.Purchase;
import com.ERP.backend.Entity.PurchaseItem;

/**
 * Explicit mapping avoids ModelMapper treating {@code List<PurchaseItem>} as {@code List<Long>}.
 */
public final class PurchaseResponseMapper {

	private PurchaseResponseMapper() {}

	public static PurchaseResponseDTO fromEntity(Purchase purchase) {
		PurchaseResponseDTO dto = new PurchaseResponseDTO();
		dto.setId(purchase.getId());
		dto.setSupplierName(purchase.getSupplier() != null ? purchase.getSupplier().getName() : null);
		dto.setPurchaseDate(purchase.getPurchaseDate());
		dto.setTotalAmount((double) purchase.getTotalAmount());
		if (purchase.getPurchaseItem() == null || purchase.getPurchaseItem().isEmpty()) {
			dto.setItems(Collections.emptyList());
		} else {
			dto.setItems(purchase.getPurchaseItem().stream()
					.map(PurchaseItem::getId)
					.filter(Objects::nonNull)
					.toList());
		}
		return dto;
	}
}
