package com.example.ExpenseSharingApp.Dto;

import java.math.BigDecimal;

import com.example.ExpenseSharingApp.Models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {

    private Long participantId;
    private BigDecimal amount;
    private User paidBy;
    private String shareType;
}
