package com.ERP.backend.Service;

import java.time.LocalDate;
import java.util.List;

import com.ERP.backend.DTO.SaleRequestDTO;
import com.ERP.backend.DTO.SaleResponseDTO;


public interface SaleService {
    SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO);
    SaleResponseDTO getSaleById(Long id);
    List<SaleResponseDTO> getAllSales();
    void deleteSale(Long id);
    Double getTotalSalesByDate(LocalDate date);
    Double getTotalSalesBetweenDates(LocalDate start, LocalDate end);
    Double calculateProfitBetweenDates(LocalDate start, LocalDate end);
}
