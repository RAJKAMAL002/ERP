package com.ERP.backend.Service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.SaleRequestDTO;
import com.ERP.backend.DTO.SaleResponseDTO;
import com.ERP.backend.Entity.Customer;
import com.ERP.backend.Entity.Sale;
import com.ERP.backend.Repository.CustomerRepo;
import com.ERP.backend.Repository.SaleRepo;
import com.ERP.backend.Service.SaleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepo saleRepo;
    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;

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

        return modelMapper.map(savedSale, SaleResponseDTO.class);
    }

    @Override
    public SaleResponseDTO getSaleById(Long id) {

        Sale sale = saleRepo.findById(id).orElseThrow(() -> new RuntimeException("Sale not found"));

        SaleResponseDTO dto = modelMapper.map(sale, SaleResponseDTO.class);

        List<Long> itemIds = sale.getSaleItems()
                .stream()
                .map(item -> item.getId())
                .toList();

        dto.setItems(itemIds);

        return dto;
    }

    @Override
    public List<SaleResponseDTO> getAllSales() {

        return saleRepo.findAll()
                .stream()
                .map(sale -> {

                    SaleResponseDTO dto = modelMapper.map(sale, SaleResponseDTO.class);

                    List<Long> itemIds = sale.getSaleItems()
                            .stream()
                            .map(item -> item.getId())
                            .toList();

                    dto.setItems(itemIds);

                    return dto;

                }).toList();
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