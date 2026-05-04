package com.ERP.backend.Service.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.ProductRequestDTO;
import com.ERP.backend.DTO.ProductResponseDTO;
import com.ERP.backend.Entity.Product;
import com.ERP.backend.Repository.ProductRepo;
import com.ERP.backend.Service.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepo productRepo;
	private final ModelMapper modelMapper;
	

	@Override
	public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
		if(productRepo.existsByName(productRequestDTO.getName())) throw new IllegalArgumentException("Product Name already exists");
		if(productRequestDTO.getMaxStockLevel() > Integer.MAX_VALUE) throw new IllegalArgumentException("Maximum Inventory Limit Reached");
		if(productRequestDTO.getMinStockLevel() < 0) throw new IllegalArgumentException("Inventory cannot be less than zero");
		Product productRequest = modelMapper.map(productRequestDTO, Product.class);
		Product product = productRepo.save(productRequest);
		return modelMapper.map(product, ProductResponseDTO.class);
	}

	@Override
	@Transactional
	public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
		Product product = productRepo.findById(id).orElseThrow();
		Product productRequest = modelMapper.map(productRequestDTO, Product.class);
		if(productRequest.getMaxStockLevel() > Integer.MAX_VALUE) throw new IllegalArgumentException("Maximum Inventory Limit Reached");
		if(productRequest.getMinStockLevel() < 0) throw new IllegalArgumentException("Inventory cannot be less than zero");
		product.setCategory(productRequest.getCategory());
		product.setMaxStockLevel(productRequest.getMaxStockLevel());
		product.setMinStockLevel(productRequest.getMinStockLevel());
		product.setName(productRequest.getName());
		product.setPurchasePrice(productRequest.getPurchasePrice());
		product.setSellingPrice(productRequest.getSellingPrice());
		return modelMapper.map(product, ProductResponseDTO.class);
	}

	@Override
	public ProductResponseDTO getProductById(Long id) {
		Product product = productRepo.findById(id).orElseThrow();
		return modelMapper.map(product, ProductResponseDTO.class);
	}

	@Override
	public List<ProductResponseDTO> getAllProducts() {
		List<Product> products = productRepo.findAll();
		return products.stream()
				      .map((product) -> modelMapper.map(product, ProductResponseDTO.class))
				      .toList();
	}

	@Override
	public void deleteProduct(Long id) {
		Product product = productRepo.findById(id).orElseThrow();
		productRepo.delete(product);
	}
    
	@Transactional
	@Override
	public void increaseStock(Long productId, Integer quantity) {
		Product product = productRepo.findById(productId).orElseThrow();
		Integer inventory = product.getInventory() + quantity;
		if(inventory > product.getMaxStockLevel()) throw new IllegalArgumentException("Inventory cannot exceed Maximum Stock Level");
		product.setInventory(inventory);
	}
    
	@Transactional
	@Override
	public void decreaseStock(Long productId, Integer quantity) {
		Product product = productRepo.findById(productId).orElseThrow();
		Integer inventory = product.getInventory() - quantity;
		if(inventory < 0) throw new IllegalArgumentException("Inventory cannot be less than zero");
		if(inventory < product.getMinStockLevel()) System.out.println("Inventory cannot be less than Minimum Stock Level");
		product.setInventory(inventory);
	}

	@Override
	public List<ProductResponseDTO> getLowStockProducts() {
		List<Product> products = productRepo.findLowInventoryProduct();
		return products.stream()
			      .map((product) -> modelMapper.map(product, ProductResponseDTO.class))
			      .toList();
	}

	@Override
	public List<ProductResponseDTO> searchProducts(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void updateStock(Long productId, Integer quantity) {
		Product product = productRepo.findById(productId).orElseThrow();
		product.setInventory(quantity);
	}

	@Override
	public Integer getInventory() {
		return productRepo.getTotalInventory();
	}

}
