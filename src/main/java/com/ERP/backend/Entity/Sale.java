package com.ERP.backend.Entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(optional = false)
	private Customer customer;
	
	@Column(nullable = false)
	private int totalAmount;
	
	@Column(nullable = false)
	private String paymentMode;
	
	@Column(nullable = false)
	private LocalDateTime saleDate;
	
	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
	private List<SaleItem> saleItems;
	
	@CreationTimestamp
	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;
}
