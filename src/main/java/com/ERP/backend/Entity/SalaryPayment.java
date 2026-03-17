package com.ERP.backend.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class SalaryPayment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(optional = false)
	private Employee employee;
	
	@Column(nullable = false)
	private int amount;
	
	@Column(nullable = false)
	private String month;
	
	@Column(nullable = false)
	private LocalDateTime paymentDate;
	
	@CreationTimestamp
	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;
}
