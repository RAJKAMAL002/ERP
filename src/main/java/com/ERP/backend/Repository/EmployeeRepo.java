package com.ERP.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ERP.backend.Entity.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, Long>{

}
