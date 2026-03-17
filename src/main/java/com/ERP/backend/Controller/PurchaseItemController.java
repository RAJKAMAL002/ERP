package com.ERP.backend.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ERP.backend.DTO.PurchaseItemRequestDTO;
import com.ERP.backend.DTO.PurchaseItemResponseDTO;
import com.ERP.backend.Service.PurchaseItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("erp/api/purchase-items")
@RequiredArgsConstructor
public class PurchaseItemController {

    private final PurchaseItemService purchaseItemService;

    // Create Purchase Item
    @PostMapping
    public ResponseEntity<PurchaseItemResponseDTO> createPurchaseItem(
            @RequestBody PurchaseItemRequestDTO dto) {

        PurchaseItemResponseDTO response = purchaseItemService.createPurchaseItem(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update Purchase Item
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseItemResponseDTO> updatePurchaseItem(
            @PathVariable Long id,
            @RequestBody PurchaseItemRequestDTO dto) {

        PurchaseItemResponseDTO response =
                purchaseItemService.updateProductFromPurchaseItem(dto, id);

        return ResponseEntity.ok(response);
    }

    // Get Purchase Item By ID
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseItemResponseDTO> getPurchaseItemById(
            @PathVariable Long id) {

        PurchaseItemResponseDTO response =
                purchaseItemService.getPurchaseItemByID(id);

        return ResponseEntity.ok(response);
    }

    // Get All Purchase Items
    @GetMapping
    public ResponseEntity<List<PurchaseItemResponseDTO>> getAllPurchaseItems() {

        List<PurchaseItemResponseDTO> items =
                purchaseItemService.getPurchaseItems();

        return ResponseEntity.ok(items);
    }

    // Get Purchase Items By Purchase ID
    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<List<PurchaseItemResponseDTO>> getItemsByPurchaseId(
            @PathVariable Long purchaseId) {

        List<PurchaseItemResponseDTO> items =
                purchaseItemService.getItemsByPurchaseId(purchaseId);

        return ResponseEntity.ok(items);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<PurchaseItemResponseDTO> deletePurchaseItem(@PathVariable Long id) {

        PurchaseItemResponseDTO response = purchaseItemService.deleteProductFromPurchaseItem(id);

        return ResponseEntity.ok(response);
    }
}