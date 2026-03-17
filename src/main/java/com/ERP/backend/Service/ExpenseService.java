package com.ERP.backend.Service;

import java.time.LocalDate;
import java.util.List;

import com.ERP.backend.Entity.Expense;


public interface ExpenseService {
    Expense createExpense(Expense expense);
    Expense updateExpense(Long id, Expense expense);
    Expense getExpenseById(Long id);
    List<Expense> getAllExpenses();
    void deleteExpense(Long id);

    Double getTotalExpenseByDate(LocalDate date);
    Double getTotalExpenseBetweenDates(LocalDate start, LocalDate end);
}
