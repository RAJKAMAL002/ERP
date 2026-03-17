package com.ERP.backend.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ERP.backend.DTO.SaleRequestDTO;
import com.ERP.backend.DTO.SaleResponseDTO;
import com.ERP.backend.Service.SaleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("erp/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    // Create Sale Order
    @PostMapping
    public ResponseEntity<SaleResponseDTO> createSale(@RequestBody SaleRequestDTO request) {

        SaleResponseDTO sale = saleService.createSale(request);

        return ResponseEntity.ok(sale);
    }

    // Get Sale By Id
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> getSaleById(@PathVariable Long id) {

        SaleResponseDTO sale = saleService.getSaleById(id);

        return ResponseEntity.ok(sale);
    }

    // Get All Sales
    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> getAllSales() {

        List<SaleResponseDTO> sales = saleService.getAllSales();

        return ResponseEntity.ok(sales);
    }

    // Delete Sale
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable Long id) {

        saleService.deleteSale(id);

        return ResponseEntity.ok("Sale deleted successfully");
    }

    // Get Total Sales By Date
    @GetMapping("/total/date")
    public ResponseEntity<Double> getTotalSalesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Double total = saleService.getTotalSalesByDate(date);

        return ResponseEntity.ok(total);
    }

    // Get Total Sales Between Dates
    @GetMapping("/total/range")
    public ResponseEntity<Double> getTotalSalesBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        Double total = saleService.getTotalSalesBetweenDates(start, end);

        return ResponseEntity.ok(total);
    }

    // Calculate Profit Between Dates
    @GetMapping("/profit")
    public ResponseEntity<Double> calculateProfitBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        Double profit = saleService.calculateProfitBetweenDates(start, end);

        return ResponseEntity.ok(profit);
    }
}