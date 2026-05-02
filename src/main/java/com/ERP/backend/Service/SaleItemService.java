package com.ERP.backend.Service;

import java.util.List;

import com.ERP.backend.DTO.SaleItemRequestDTO;
import com.ERP.backend.DTO.SaleItemResponseDTO;

public interface SaleItemService {
    SaleItemResponseDTO createSaleItem(Long saleId, SaleItemRequestDTO request);
    SaleItemResponseDTO updateSaleItem(Long saleItemId, Long saleId, SaleItemRequestDTO request);
    SaleItemResponseDTO getSaleItemById(Long saleItemId);
    List<SaleItemResponseDTO> getSaleItemsBySaleId(Long saleId);
    List<SaleItemResponseDTO> getAllSaleItems();
    SaleItemResponseDTO deleteSaleItem(Long saleItemId);
}

