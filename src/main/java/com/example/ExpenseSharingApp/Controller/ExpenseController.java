package com.example.ExpenseSharingApp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExpenseSharingApp.Dto.ExpenseDto;
import com.example.ExpenseSharingApp.Dto.ParticipantDto;
import com.example.ExpenseSharingApp.Exceptions.ExpenseException;
import com.example.ExpenseSharingApp.Models.Expense;
import com.example.ExpenseSharingApp.Models.Participant;
import com.example.ExpenseSharingApp.Service.ServiceImpl.ExpenseServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class ExpenseController {
    
      @Autowired
      private ExpenseServiceImpl serviceImpl;
       
      @PostMapping("/public/create/expense/{createdBy}")
      public ResponseEntity<ExpenseDto> createExpense(@Valid @RequestBody ExpenseDto expenseDto, @PathVariable Integer createdBy) throws ExpenseException{
              ExpenseDto expenseDto2=serviceImpl.createExpense(expenseDto, createdBy);            
               return new ResponseEntity<>(expenseDto2,HttpStatus.CREATED);
      }

      @GetMapping("/public/expense/{id}")
      public ResponseEntity<ExpenseDto> getExpense(@PathVariable Long id) throws ExpenseException{
             
            ExpenseDto expenseDto=serviceImpl.getExpenseById(id);
            return new ResponseEntity<>(expenseDto,HttpStatus.ACCEPTED);
      }
       
      @GetMapping("/public/expenseByUser/{userId}")
      public ResponseEntity<?> getExpenseByUser(@PathVariable Integer userId) throws ExpenseException{
             
            List<ParticipantDto>expenseDto=serviceImpl.getExpensesByUser(userId);
            return new ResponseEntity<>(expenseDto,HttpStatus.ACCEPTED);
      }

      @GetMapping("/public/expense/")
      public ResponseEntity<List<ExpenseDto>> getExpense() throws ExpenseException{
             
            List<ExpenseDto> expenseDtos=serviceImpl.getAllExpense();
            return new ResponseEntity<>(expenseDtos,HttpStatus.ACCEPTED);
      }

}
