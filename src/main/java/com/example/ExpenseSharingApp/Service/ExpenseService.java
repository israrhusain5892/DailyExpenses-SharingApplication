package com.example.ExpenseSharingApp.Service;

import java.util.List;

import com.example.ExpenseSharingApp.Dto.ExpenseDto;
import com.example.ExpenseSharingApp.Exceptions.ExpenseException;

public interface ExpenseService {
    
      public ExpenseDto createExpense(ExpenseDto expenseDto, Integer creatorId) throws ExpenseException;
      public ExpenseDto getExpenseById(Long id) throws ExpenseException;
      public List<ExpenseDto> getAllExpense() throws ExpenseException;
}
