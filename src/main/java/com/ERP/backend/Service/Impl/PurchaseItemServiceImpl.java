package com.ERP.backend.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.PurchaseItemRequestDTO;
import com.ERP.backend.DTO.PurchaseItemResponseDTO;
import com.ERP.backend.Entity.Product;
import com.ERP.backend.Entity.Purchase;
import com.ERP.backend.Entity.PurchaseItem;
import com.ERP.backend.Repository.ProductRepo;
import com.ERP.backend.Repository.PurchaseItemRepo;
import com.ERP.backend.Repository.PurchaseRepo;
import com.ERP.backend.Service.ProductService;
import com.ERP.backend.Service.PurchaseItemService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseItemServiceImpl implements PurchaseItemService{

    private final PurchaseItemRepo purchaseItemRepo;
    private final PurchaseRepo purchaseRepo;
    private final ProductRepo productRepo;
    private final ProductService productService;
    private final ModelMapper modelMapper;
	
	@Override
	@Transactional
	public PurchaseItemResponseDTO createPurchaseItem(PurchaseItemRequestDTO dto) {

	    Purchase purchase = purchaseRepo.findById(dto.getPurchaseId())
	            .orElseThrow(() -> new RuntimeException("Purchase not found"));

	    Product product = productRepo.findById(dto.getProductId())
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    int quantity = dto.getQuantity();

	    if (quantity <= 0) {
	        throw new RuntimeException("Quantity must be greater than 0");
	    }

	    int pricePerUnit = product.getPurchasePrice();
	    int totalPrice = pricePerUnit * quantity;

	    PurchaseItem purchaseItem = new PurchaseItem();
	    purchaseItem.setPurchase(purchase);
	    purchaseItem.setProduct(product);
	    purchaseItem.setQuantity(quantity);
	    purchaseItem.setPrice(totalPrice);

	    // Save purchase item
	    purchaseItem = purchaseItemRepo.save(purchaseItem);

	    // Increase stock
	    productService.increaseStock(product.getId(), quantity);

	    // Update purchase total
	    int currentTotal = purchase.getTotalAmount();
	    purchase.setTotalAmount(currentTotal + totalPrice);
	    
	    // Set Purchase items in the purchase list
	    List<PurchaseItem> items = new ArrayList<>();
	    items.add(purchaseItem);
	    purchase.setPurchaseItem(items);

	    purchaseRepo.save(purchase);

	    return modelMapper.map(purchaseItem, PurchaseItemResponseDTO.class);
	}
	
	@Override
	@Transactional
	public PurchaseItemResponseDTO updateProductFromPurchaseItem(PurchaseItemRequestDTO dto, Long purchaseItemId) {
		
		PurchaseItem purchaseItem = purchaseItemRepo.findById(purchaseItemId).orElseThrow(() -> new RuntimeException("Purchase Item not found"));
		
		if(!purchaseItem.getPurchase().getId().equals(dto.getPurchaseId())) throw new IllegalArgumentException("Purchase Order does not match");
		
	    Product product = productRepo.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
	    
	    Product oldProduct = purchaseItem.getProduct();
	    int oldInventory = oldProduct.getInventory();
	    int oldPurchaseQuantity = purchaseItem.getQuantity();
	    int oldPrice = purchaseItem.getPrice();
	    oldProduct.setInventory(oldInventory - oldPurchaseQuantity);
	    productRepo.save(oldProduct);

	    int quantity = dto.getQuantity();

	    if (quantity <= 0) throw new RuntimeException("Quantity must be greater than 0");

	    int pricePerUnit = product.getPurchasePrice();
	    int totalPrice = pricePerUnit * quantity;
	    
	    purchaseItem.setProduct(product);
	    purchaseItem.setQuantity(quantity);
	    purchaseItem.setPrice(totalPrice);

	    // Save purchase item
	    purchaseItem = purchaseItemRepo.save(purchaseItem);

	    // Increase stock
	    productService.updateStock(product.getId(), quantity);

	    // Update purchase total
	    Purchase purchase = purchaseItem.getPurchase();
	    int currentTotal = purchase.getTotalAmount() - oldPrice + totalPrice;
	    purchase.setTotalAmount(currentTotal);

	    // Set Purchase items in the purchase list
	    List<PurchaseItem> items = new ArrayList<>();
	    items.add(purchaseItem);
	    purchase.setPurchaseItem(items);
	    
	    purchaseRepo.save(purchase);

	    return modelMapper.map(purchaseItem, PurchaseItemResponseDTO.class);
	}
    
	@Override
	@Transactional
	public PurchaseItemResponseDTO deleteProductFromPurchaseItem(Long purchaseItemId) {
		
		PurchaseItem purchaseItem = purchaseItemRepo.findById(purchaseItemId).orElseThrow(() -> new RuntimeException("Purchase Item not found"));
		Purchase purchase = purchaseItem.getPurchase();
	    Product product = purchaseItem.getProduct();
	    
	    // Reduce inventory
	    productService.decreaseStock(product.getId(), purchaseItem.getQuantity());

	    // Update purchase total
	    purchase.setTotalAmount(purchase.getTotalAmount() - purchaseItem.getPrice());
	    
	    // Set Purchase items in the purchase list
	    List<PurchaseItem> items = purchase.getPurchaseItem();
	    items.remove(purchaseItem);
	    purchase.setPurchaseItem(items);
	    
	    purchaseRepo.save(purchase);

	    // Delete purchase item
	    purchaseItemRepo.delete(purchaseItem);

	    return modelMapper.map(purchaseItem, PurchaseItemResponseDTO.class);
	}
	

	@Override
	public List<PurchaseItemResponseDTO> getItemsByPurchaseId(Long purchaseId) {
        return purchaseItemRepo.findByPurchaseId(purchaseId)
                .stream()
                .map(item -> modelMapper.map(item, PurchaseItemResponseDTO.class))
                .toList();
	}

	@Override
	public PurchaseItemResponseDTO getPurchaseItemByID(Long id) {

	    PurchaseItem purchaseItem = purchaseItemRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Purchase Item not found"));

	    return modelMapper.map(purchaseItem, PurchaseItemResponseDTO.class);
	}

	@Override
	public List<PurchaseItemResponseDTO> getPurchaseItems() {

	    List<PurchaseItem> purchaseItems = purchaseItemRepo.findAll();

	    return purchaseItems.stream()
	            .map(item -> modelMapper.map(item, PurchaseItemResponseDTO.class))
	            .toList();
	}

}
