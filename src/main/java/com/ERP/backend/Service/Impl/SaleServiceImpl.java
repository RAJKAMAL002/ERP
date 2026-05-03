package com.ERP.backend.Service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.SaleRequestDTO;
import com.ERP.backend.DTO.SaleResponseDTO;
import com.ERP.backend.Entity.Customer;
import com.ERP.backend.Entity.Sale;
import com.ERP.backend.Entity.SaleItem;
import com.ERP.backend.Repository.CustomerRepo;
import com.ERP.backend.Repository.SaleRepo;
import com.ERP.backend.Service.SaleService;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepo saleRepo;
    private final CustomerRepo customerRepo;

    /**
     * ModelMapper cannot map {@code List<SaleItem>} to {@code List<Long>} on {@link SaleResponseDTO#items};
     * build the DTO explicitly.
     */
    private static SaleResponseDTO toDto(Sale sale) {
        SaleResponseDTO dto = new SaleResponseDTO();
        dto.setId(sale.getId());
        dto.setCustomerId(sale.getCustomer() != null ? sale.getCustomer().getId() : null);
        dto.setSaleDate(sale.getSaleDate());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setPaymentMode(sale.getPaymentMode());
        if (sale.getSaleItems() == null || sale.getSaleItems().isEmpty()) {
            dto.setItems(Collections.emptyList());
        } else {
            dto.setItems(sale.getSaleItems().stream()
                    .map(SaleItem::getId)
                    .filter(Objects::nonNull)
                    .toList());
        }
        return dto;
    }

    @Override
    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO saleRequest) {
        Customer customer = customerRepo.findById(saleRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setPaymentMode(saleRequest.getPaymentMode());
        sale.setSaleDate(LocalDateTime.now());
        sale.setTotalAmount(0); // initially 0

        Sale savedSale = saleRepo.save(sale);

        return toDto(savedSale);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponseDTO getSaleById(Long id) {

        Sale sale = saleRepo.findById(id).orElseThrow(() -> new RuntimeException("Sale not found"));

        return toDto(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponseDTO> getAllSales() {

        return saleRepo.findAll().stream().map(SaleServiceImpl::toDto).toList();
    }

    @Override
    public void deleteSale(Long id) {

        Sale sale = saleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        saleRepo.delete(sale);
    }

    @Override
    public Double getTotalSalesByDate(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        return saleRepo.sumSalesBetweenDates(start, end);
    }

    @Override
    public Double getTotalSalesBetweenDates(LocalDate start, LocalDate end) {

        return saleRepo.sumSalesBetweenDates(
                start.atStartOfDay(),
                end.atTime(23, 59, 59)
        );
    }

    @Override
    public Double calculateProfitBetweenDates(LocalDate start, LocalDate end) {

        return saleRepo.sumSalesBetweenDates(
                start.atStartOfDay(),
                end.atTime(23, 59, 59)
        );
    }
}