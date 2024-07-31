package com.example.ExpenseSharingApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ExpenseSharingApp.Models.Expense;
import com.example.ExpenseSharingApp.Models.Participant;


@Repository
public interface ParticipantRepository extends JpaRepository<Participant,Long> {

    List<Participant> findByExpense(Expense expense);
    List<Participant> findByPaidBy(Integer userId);
    
}

