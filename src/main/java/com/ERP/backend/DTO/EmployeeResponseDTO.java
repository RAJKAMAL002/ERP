package com.ERP.backend.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private String role;
    private int salary;
    private String joiningDate;
    private LocalDateTime createdAt;
}

