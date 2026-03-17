package com.ERP.backend.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ERP.backend.DTO.PurchaseRequestDTO;
import com.ERP.backend.DTO.PurchaseResponseDTO;
import com.ERP.backend.Service.PurchaseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("erp/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    // Create Purchase
    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> createPurchase(
            @RequestBody PurchaseRequestDTO request) {

        PurchaseResponseDTO response = purchaseService.createPurchase(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get Purchase by ID
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> getPurchaseById(
            @PathVariable Long id) {

        PurchaseResponseDTO response = purchaseService.getPurchaseById(id);
        return ResponseEntity.ok(response);
    }

    // Get All Purchases
    @GetMapping
    public ResponseEntity<List<PurchaseResponseDTO>> getAllPurchases() {

        List<PurchaseResponseDTO> purchases = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchases);
    }

    // Delete Purchase
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePurchase(@PathVariable Long id) {

        purchaseService.deletePurchase(id);
        return ResponseEntity.ok("Purchase deleted successfully");
    }

    // Total purchase amount by date
    @GetMapping("/total/date")
    public ResponseEntity<Double> getTotalPurchaseByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Double total = purchaseService.getTotalPurchaseAmountByDate(date);
        return ResponseEntity.ok(total);
    }

    // Total purchase between dates
    @GetMapping("/total/between")
    public ResponseEntity<Double> getTotalPurchaseBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        Double total = purchaseService.getTotalPurchaseAmountBetweenDates(start, end);
        return ResponseEntity.ok(total);
    }
}
