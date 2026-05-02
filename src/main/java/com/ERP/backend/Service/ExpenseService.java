package com.ERP.backend.Service;

import java.time.LocalDate;
import java.util.List;

import com.ERP.backend.DTO.ExpenseRequestDTO;
import com.ERP.backend.DTO.ExpenseResponseDTO;


public interface ExpenseService {
    ExpenseResponseDTO createExpense(ExpenseRequestDTO expense);
    ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO expense);
    ExpenseResponseDTO getExpenseById(Long id);
    List<ExpenseResponseDTO> getAllExpenses();
    void deleteExpense(Long id);

    Double getTotalExpenseByDate(LocalDate date);
    Double getTotalExpenseBetweenDates(LocalDate start, LocalDate end);
}
