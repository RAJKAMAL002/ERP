package com.ERP.backend.Service;

import java.util.List;

import com.ERP.backend.DTO.PurchaseItemRequestDTO;
import com.ERP.backend.DTO.PurchaseItemResponseDTO;

public interface PurchaseItemService {
	public PurchaseItemResponseDTO createPurchaseItem(PurchaseItemRequestDTO dto);
	public PurchaseItemResponseDTO updateProductFromPurchaseItem(PurchaseItemRequestDTO dto, Long purchaseItemId);
	public PurchaseItemResponseDTO getPurchaseItemByID(Long id);
	public PurchaseItemResponseDTO deleteProductFromPurchaseItem(Long purchaseItemId);
	public List<PurchaseItemResponseDTO> getPurchaseItems();
	public List<PurchaseItemResponseDTO> getItemsByPurchaseId(Long purchaseId);
}
