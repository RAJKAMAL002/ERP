package com.ERP.backend.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ERP.backend.DTO.SaleItemRequestDTO;
import com.ERP.backend.DTO.SaleItemResponseDTO;
import com.ERP.backend.Service.SaleItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("erp/api/sale-items")
@RequiredArgsConstructor
public class SaleItemController {

    private final SaleItemService saleItemService;

    @PostMapping("/sale/{saleId}")
    public ResponseEntity<SaleItemResponseDTO> createSaleItem(@PathVariable Long saleId, @RequestBody SaleItemRequestDTO request) {
        SaleItemResponseDTO response = saleItemService.createSaleItem(saleId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{saleItemId}/sale/{saleId}")
    public ResponseEntity<SaleItemResponseDTO> updateSaleItem(
            @PathVariable Long saleItemId,
            @PathVariable Long saleId,
            @RequestBody SaleItemRequestDTO request) {
        return ResponseEntity.ok(saleItemService.updateSaleItem(saleItemId, saleId, request));
    }

    @GetMapping("/{saleItemId}")
    public ResponseEntity<SaleItemResponseDTO> getSaleItemById(@PathVariable Long saleItemId) {
        return ResponseEntity.ok(saleItemService.getSaleItemById(saleItemId));
    }

    @GetMapping("/sale/{saleId}")
    public ResponseEntity<List<SaleItemResponseDTO>> getSaleItemsBySaleId(@PathVariable Long saleId) {
        return ResponseEntity.ok(saleItemService.getSaleItemsBySaleId(saleId));
    }

    @GetMapping
    public ResponseEntity<List<SaleItemResponseDTO>> getAllSaleItems() {
        return ResponseEntity.ok(saleItemService.getAllSaleItems());
    }

    @DeleteMapping("/{saleItemId}")
    public ResponseEntity<SaleItemResponseDTO> deleteSaleItem(@PathVariable Long saleItemId) {
        return ResponseEntity.ok(saleItemService.deleteSaleItem(saleItemId));
    }
}

