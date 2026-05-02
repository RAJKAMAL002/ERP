package com.ERP.backend.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.SaleItemRequestDTO;
import com.ERP.backend.DTO.SaleItemResponseDTO;
import com.ERP.backend.Entity.Product;
import com.ERP.backend.Entity.Sale;
import com.ERP.backend.Entity.SaleItem;
import com.ERP.backend.Repository.ProductRepo;
import com.ERP.backend.Repository.SaleItemRepo;
import com.ERP.backend.Repository.SaleRepo;
import com.ERP.backend.Service.ProductService;
import com.ERP.backend.Service.SaleItemService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleItemServiceImpl implements SaleItemService {

    private final SaleItemRepo saleItemRepo;
    private final SaleRepo saleRepo;
    private final ProductRepo productRepo;
    private final ProductService productService;

    private SaleItemResponseDTO toDto(SaleItem saleItem) {
        Product product = saleItem.getProduct();
        int total = saleItem.getPrice();
        return new SaleItemResponseDTO(
                product.getId(),
                product.getName(),
                saleItem.getQuantity(),
                product.getSellingPrice(),
                total
        );
    }

    @Override
    @Transactional
    public SaleItemResponseDTO createSaleItem(Long saleId, SaleItemRequestDTO request) {
        Sale sale = saleRepo.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found"));
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

        int quantity = request.getQuantity();
        if (quantity <= 0) throw new RuntimeException("Quantity must be greater than 0");

        int unitPrice = product.getSellingPrice();
        int totalPrice = unitPrice * quantity;

        SaleItem saleItem = new SaleItem();
        saleItem.setSale(sale);
        saleItem.setProduct(product);
        saleItem.setQuantity(quantity);
        saleItem.setPrice(totalPrice);

        SaleItem saved = saleItemRepo.save(saleItem);

        productService.decreaseStock(product.getId(), quantity);

        sale.setTotalAmount(sale.getTotalAmount() + totalPrice);
        saleRepo.save(sale);

        return toDto(saved);
    }

    @Override
    @Transactional
    public SaleItemResponseDTO updateSaleItem(Long saleItemId, Long saleId, SaleItemRequestDTO request) {
        SaleItem saleItem = saleItemRepo.findById(saleItemId).orElseThrow(() -> new RuntimeException("Sale item not found"));
        if (!saleItem.getSale().getId().equals(saleId)) throw new IllegalArgumentException("Sale order does not match");

        Product newProduct = productRepo.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        int newQty = request.getQuantity();
        if (newQty <= 0) throw new RuntimeException("Quantity must be greater than 0");

        Sale sale = saleItem.getSale();

        // Rollback old stock + totals
        Product oldProduct = saleItem.getProduct();
        int oldQty = saleItem.getQuantity();
        int oldTotal = saleItem.getPrice();

        productService.increaseStock(oldProduct.getId(), oldQty);
        sale.setTotalAmount(sale.getTotalAmount() - oldTotal);

        // Apply new item
        int newTotal = newProduct.getSellingPrice() * newQty;
        saleItem.setProduct(newProduct);
        saleItem.setQuantity(newQty);
        saleItem.setPrice(newTotal);

        SaleItem saved = saleItemRepo.save(saleItem);

        productService.decreaseStock(newProduct.getId(), newQty);
        sale.setTotalAmount(sale.getTotalAmount() + newTotal);
        saleRepo.save(sale);

        return toDto(saved);
    }

    @Override
    public SaleItemResponseDTO getSaleItemById(Long saleItemId) {
        SaleItem saleItem = saleItemRepo.findById(saleItemId).orElseThrow(() -> new RuntimeException("Sale item not found"));
        return toDto(saleItem);
    }

    @Override
    public List<SaleItemResponseDTO> getSaleItemsBySaleId(Long saleId) {
        return saleItemRepo.findBySaleId(saleId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<SaleItemResponseDTO> getAllSaleItems() {
        return saleItemRepo.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional
    public SaleItemResponseDTO deleteSaleItem(Long saleItemId) {
        SaleItem saleItem = saleItemRepo.findById(saleItemId).orElseThrow(() -> new RuntimeException("Sale item not found"));
        Sale sale = saleItem.getSale();

        productService.increaseStock(saleItem.getProduct().getId(), saleItem.getQuantity());
        sale.setTotalAmount(sale.getTotalAmount() - saleItem.getPrice());
        saleRepo.save(sale);

        saleItemRepo.delete(saleItem);
        return toDto(saleItem);
    }
}

