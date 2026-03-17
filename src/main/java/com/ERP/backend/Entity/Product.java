package com.ERP.backend.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String Category;
	
	@Column(nullable = false)
	private int purchasePrice;
	
	@Column(nullable = false)
	private int sellingPrice;
	
	@Column(nullable = false)
	private int minStockLevel;
	
	@Column(nullable = false)
	private int maxStockLevel;
	
	@Column(nullable = false)
	private int inventory;
	
	@CreationTimestamp
	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;
}
