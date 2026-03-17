package com.ERP.backend.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ERP.backend.DTO.PurchaseResponseDTO;
import com.ERP.backend.DTO.SupplierRequestDTO;
import com.ERP.backend.DTO.SupplierResponseDTO;
import com.ERP.backend.Service.SupplierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("erp/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    // Create Supplier
    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(
            @RequestBody SupplierRequestDTO supplierRequest) {

        SupplierResponseDTO response = supplierService.createSupplier(supplierRequest);

        return ResponseEntity
                .status(201)
                .body(response);
    }

    // Update Supplier
    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(
            @PathVariable Long id,
            @RequestBody SupplierRequestDTO supplierRequest) {

        SupplierResponseDTO response = supplierService.updateSupplier(id, supplierRequest);

        return ResponseEntity.ok(response);
    }

    // Get Supplier by ID
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(
            @PathVariable Long id) {

        SupplierResponseDTO response = supplierService.getSupplierById(id);

        return ResponseEntity.ok(response);
    }

    // Get All Suppliers
    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers() {

        List<SupplierResponseDTO> response = supplierService.getAllSuppliers();

        return ResponseEntity.ok(response);
    }

    // Delete Supplier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {

        supplierService.deleteSupplier(id);

        return ResponseEntity.noContent().build();
    }

    // Get Purchases of Supplier
    @GetMapping("/{supplierId}/purchases")
    public ResponseEntity<List<PurchaseResponseDTO>> getSupplierPurchases(
            @PathVariable Long supplierId) {

        List<PurchaseResponseDTO> purchases =
                supplierService.getSupplierPurchases(supplierId);

        return ResponseEntity.ok(purchases);
    }
}
