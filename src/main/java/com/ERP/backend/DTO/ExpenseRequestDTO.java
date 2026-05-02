package com.ERP.backend.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequestDTO {
    private String type;
    private int amount;
    private LocalDateTime date;
    private String description;
}

