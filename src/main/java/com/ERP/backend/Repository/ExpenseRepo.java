package com.ERP.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ERP.backend.Entity.Expense;


public interface ExpenseRepo extends JpaRepository<Expense, Long>{

}
