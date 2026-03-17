package com.ERP.backend.Service;

import java.time.LocalDate;
import java.util.List;

import com.ERP.backend.DTO.PurchaseRequestDTO;
import com.ERP.backend.DTO.PurchaseResponseDTO;

public interface PurchaseService {
    PurchaseResponseDTO createPurchase(PurchaseRequestDTO purchase);
    PurchaseResponseDTO getPurchaseById(Long id);
    List<PurchaseResponseDTO> getAllPurchases();
    void deletePurchase(Long id);
    Double getTotalPurchaseAmountByDate(LocalDate date);
    Double getTotalPurchaseAmountBetweenDates(LocalDate start, LocalDate end);
}
