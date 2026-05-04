package com.ERP.backend.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ERP.backend.DTO.ProductRequestDTO;
import com.ERP.backend.DTO.ProductResponseDTO;
import com.ERP.backend.Service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
	private final ProductService productService; 
	
	@PostMapping
	public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO){
		return ResponseEntity.ok(productService.createProduct(productRequestDTO));
	}
	
	@GetMapping
	public ResponseEntity<List<ProductResponseDTO>> fetchProducts(){
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> fetchProductsById(@PathVariable("id") Long id){
		return ResponseEntity.ok(productService.getProductById(id));
	}
	
	@GetMapping("/low-stock")
	public ResponseEntity<List<ProductResponseDTO>> fetchLowStockProduct(){
		return ResponseEntity.ok(productService.getLowStockProducts());
	}
	
	@GetMapping("/total-inventory")
	public ResponseEntity<Integer> fetchTotalInventory(){
		return ResponseEntity.ok(productService.getInventory());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id){
		productService.deleteProduct(id);
	    return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDTO productRequestDTO){
		return ResponseEntity.ok(productService.updateProduct(id, productRequestDTO));
	}
}
