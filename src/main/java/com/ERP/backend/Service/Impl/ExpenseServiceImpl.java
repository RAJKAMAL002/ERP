package com.ERP.backend.Service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.ExpenseRequestDTO;
import com.ERP.backend.DTO.ExpenseResponseDTO;
import com.ERP.backend.Entity.Expense;
import com.ERP.backend.Repository.ExpenseRepo;
import com.ERP.backend.Service.ExpenseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepo expenseRepo;
    private final ModelMapper modelMapper;

    @Override
    public ExpenseResponseDTO createExpense(ExpenseRequestDTO expense) {
        Expense entity = modelMapper.map(expense, Expense.class);
        Expense saved = expenseRepo.save(entity);
        return modelMapper.map(saved, ExpenseResponseDTO.class);
    }

    @Override
    @Transactional
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO expense) {
        Expense existing = expenseRepo.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        existing.setType(expense.getType());
        existing.setAmount(expense.getAmount());
        existing.setDate(expense.getDate());
        existing.setDescription(expense.getDescription());
        return modelMapper.map(existing, ExpenseResponseDTO.class);
    }

    @Override
    public ExpenseResponseDTO getExpenseById(Long id) {
        Expense expense = expenseRepo.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        return modelMapper.map(expense, ExpenseResponseDTO.class);
    }

    @Override
    public List<ExpenseResponseDTO> getAllExpenses() {
        return expenseRepo.findAll()
                .stream()
                .map(e -> modelMapper.map(e, ExpenseResponseDTO.class))
                .toList();
    }

    @Override
    public void deleteExpense(Long id) {
        Expense expense = expenseRepo.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepo.delete(expense);
    }

    @Override
    public Double getTotalExpenseByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);
        return expenseRepo.sumExpenseBetweenDates(start, end);
    }

    @Override
    public Double getTotalExpenseBetweenDates(LocalDate start, LocalDate end) {
        return expenseRepo.sumExpenseBetweenDates(
                start.atStartOfDay(),
                end.atTime(23, 59, 59)
        );
    }
}

