package com.example.ExpenseSharingApp.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ExpenseSharingApp.Models.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}

