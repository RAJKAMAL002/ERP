package com.ERP.backend.Service.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.PurchaseResponseDTO;
import com.ERP.backend.DTO.SupplierRequestDTO;
import com.ERP.backend.DTO.SupplierResponseDTO;
import com.ERP.backend.Entity.Supplier;
import com.ERP.backend.mapper.PurchaseResponseMapper;
import com.ERP.backend.Repository.PurchaseRepo;
import com.ERP.backend.Repository.SupplierRepo;
import com.ERP.backend.Service.SupplierService;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
	
	private final ModelMapper modelMapper;
	private final SupplierRepo supplierRepo;
	private final PurchaseRepo purchaseRepo;

	@Override
	public SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequest) {
		if(supplierRepo.existsByGstNumber(supplierRequest.getGstNumber()))  throw new IllegalArgumentException("Supplier with GST Number already exists");
		if(supplierRepo.existsByPhone(supplierRequest.getPhone()))  throw new IllegalArgumentException("Supplier with Phone Number already exists");
		if(supplierRepo.existsByEmail(supplierRequest.getEmail()))  throw new IllegalArgumentException("Supplier with Email already exists");
		Supplier supplier = modelMapper.map(supplierRequest, Supplier.class);
		return modelMapper.map(supplierRepo.save(supplier), SupplierResponseDTO.class);
	}

	@Override
	@Transactional
	public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierRequest) {
		Supplier supplier = supplierRepo.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
		modelMapper.map(supplierRequest, supplier);
		return modelMapper.map(supplier, SupplierResponseDTO.class);
	}

	@Override
	public SupplierResponseDTO getSupplierById(Long id) {
		Supplier supplier = supplierRepo.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
		return modelMapper.map(supplier, SupplierResponseDTO.class);
	}

	@Override
	public List<SupplierResponseDTO> getAllSuppliers() {
		List<Supplier> suppliers = supplierRepo.findAll();
		return suppliers.stream()
				        .map((supplier) -> modelMapper.map(supplier, SupplierResponseDTO.class))
				        .toList();
	}

	@Override
	public void deleteSupplier(Long id) {
		Supplier supplier = supplierRepo.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
		supplierRepo.delete(supplier);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PurchaseResponseDTO> getSupplierPurchases(Long supplierId) {
		return purchaseRepo.findBySupplierId(supplierId).stream()
		        .map(PurchaseResponseMapper::fromEntity)
		        .toList();
	}

}
