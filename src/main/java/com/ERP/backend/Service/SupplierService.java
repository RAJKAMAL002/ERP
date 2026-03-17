package com.ERP.backend.Service;

import java.util.List;


import com.ERP.backend.DTO.PurchaseResponseDTO;
import com.ERP.backend.DTO.SupplierRequestDTO;
import com.ERP.backend.DTO.SupplierResponseDTO;

public interface SupplierService {
    SupplierResponseDTO createSupplier(SupplierRequestDTO supplier);
    SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplier);
    SupplierResponseDTO getSupplierById(Long id);
    List<SupplierResponseDTO> getAllSuppliers();
    void deleteSupplier(Long id);
    List<PurchaseResponseDTO> getSupplierPurchases(Long supplierId);
}
