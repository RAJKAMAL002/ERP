package com.ERP.backend.Entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Supplier supplier;
	
	@Column(nullable = false)
	private int totalAmount;
	
	@Column(nullable = false)
	private LocalDateTime purchaseDate;
	
	@OneToMany(mappedBy = "purchase")
	private List<PurchaseItem> purchaseItem;
	
	@CreationTimestamp
	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;
}
