package com.ERP.backend.Service;

import java.util.List;

import com.ERP.backend.DTO.ProductRequestDTO;
import com.ERP.backend.DTO.ProductResponseDTO;

public interface ProductService {
	// CRUD
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO product);
    ProductResponseDTO getProductById(Long id);
    List<ProductResponseDTO> getAllProducts();
    void deleteProduct(Long id);
    Integer getInventory();

    // Stock Management
    void increaseStock(Long productId, Integer quantity);
    void updateStock(Long productId, Integer quantity);
    void decreaseStock(Long productId, Integer quantity);

    // Alerts
    List<ProductResponseDTO> getLowStockProducts();

    // Search
    List<ProductResponseDTO> searchProducts(String keyword);
}
