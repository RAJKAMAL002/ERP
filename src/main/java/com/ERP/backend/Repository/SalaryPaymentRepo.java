package com.ERP.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ERP.backend.Entity.SalaryPayment;


public interface SalaryPaymentRepo extends JpaRepository<SalaryPayment, Long>{

}
