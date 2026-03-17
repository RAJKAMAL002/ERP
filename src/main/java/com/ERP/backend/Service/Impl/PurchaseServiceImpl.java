package com.ERP.backend.Service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.PurchaseRequestDTO;
import com.ERP.backend.DTO.PurchaseResponseDTO;
import com.ERP.backend.Entity.Purchase;
import com.ERP.backend.Entity.Supplier;
import com.ERP.backend.Repository.PurchaseRepo;
import com.ERP.backend.Repository.SupplierRepo;
import com.ERP.backend.Service.PurchaseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{

    private final PurchaseRepo purchaseRepo;
    private final SupplierRepo supplierRepo;
    private final ModelMapper modelMapper;
	
	@Override
	@Transactional
	public PurchaseResponseDTO createPurchase(PurchaseRequestDTO purchaseRequest) {
	    Purchase purchase = new Purchase();
	    purchase.setPurchaseDate(purchaseRequest.getPurchaseDate());
	    Supplier supplier = supplierRepo.findById(purchaseRequest.getSupplierId()).orElseThrow(() -> new RuntimeException("Supplier not found"));
	    purchase.setSupplier(supplier);
	    Purchase savedPurchase = purchaseRepo.save(purchase);
	    return modelMapper.map(savedPurchase, PurchaseResponseDTO.class);
	}

	@Override
	public PurchaseResponseDTO getPurchaseById(Long id) {

	    Purchase purchase = purchaseRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Purchase not found"));

	    PurchaseResponseDTO dto = modelMapper.map(purchase, PurchaseResponseDTO.class);

	    List<Long> itemIds = purchase.getPurchaseItem()
	            .stream()
	            .map(item -> item.getId())
	            .toList();

	    dto.setItems(itemIds);

	    return dto;
	}

	@Override
	public List<PurchaseResponseDTO> getAllPurchases() {

	    return purchaseRepo.findAll()
	            .stream()
	            .map(p -> {

	                PurchaseResponseDTO dto = modelMapper.map(p, PurchaseResponseDTO.class);

	                List<Long> itemIds = p.getPurchaseItem()
	                        .stream()
	                        .map(item -> item.getId())
	                        .toList();

	                dto.setItems(itemIds);

	                return dto;
	            })
	            .toList();
	}

	@Override
	public void deletePurchase(Long id) {
		Purchase purchase = purchaseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        purchaseRepo.delete(purchase);
	}

	@Override
	public Double getTotalPurchaseAmountByDate(LocalDate date) {
		LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23,59,59);

        return purchaseRepo.sumPurchaseBetweenDates(start, end);
	}

	@Override
	public Double getTotalPurchaseAmountBetweenDates(LocalDate start, LocalDate end) {
		return purchaseRepo.sumPurchaseBetweenDates(
                start.atStartOfDay(),
                end.atTime(23,59,59)
        );
	}

}
