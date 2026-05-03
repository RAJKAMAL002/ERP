package com.ERP.backend.Service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.PurchaseRequestDTO;
import com.ERP.backend.DTO.PurchaseResponseDTO;
import com.ERP.backend.Entity.Purchase;
import com.ERP.backend.Entity.Supplier;
import com.ERP.backend.mapper.PurchaseResponseMapper;
import com.ERP.backend.Repository.PurchaseRepo;
import com.ERP.backend.Repository.SupplierRepo;
import com.ERP.backend.Service.PurchaseService;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseRepo purchaseRepo;
	private final SupplierRepo supplierRepo;

	@Override
	@Transactional
	public PurchaseResponseDTO createPurchase(PurchaseRequestDTO purchaseRequest) {
		Purchase purchase = new Purchase();
		purchase.setPurchaseDate(purchaseRequest.getPurchaseDate());
		Supplier supplier = supplierRepo.findById(purchaseRequest.getSupplierId())
				.orElseThrow(() -> new RuntimeException("Supplier not found"));
		purchase.setSupplier(supplier);
		Purchase savedPurchase = purchaseRepo.save(purchase);
		return PurchaseResponseMapper.fromEntity(savedPurchase);
	}

	@Override
	@Transactional(readOnly = true)
	public PurchaseResponseDTO getPurchaseById(Long id) {
		Purchase purchase = purchaseRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Purchase not found"));
		return PurchaseResponseMapper.fromEntity(purchase);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PurchaseResponseDTO> getAllPurchases() {
		return purchaseRepo.findAll().stream().map(PurchaseResponseMapper::fromEntity).toList();
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
		LocalDateTime end = date.atTime(23, 59, 59);
		return purchaseRepo.sumPurchaseBetweenDates(start, end);
	}

	@Override
	public Double getTotalPurchaseAmountBetweenDates(LocalDate start, LocalDate end) {
		return purchaseRepo.sumPurchaseBetweenDates(
				start.atStartOfDay(),
				end.atTime(23, 59, 59));
	}
}
