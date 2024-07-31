package com.example.ExpenseSharingApp.Models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
     
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantId;



    private BigDecimal amount;

   
    private String shareType; 

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User paidBy;
   
   @ManyToOne
   @JoinColumn(name="expense_id")
   private Expense expense;
     
}
