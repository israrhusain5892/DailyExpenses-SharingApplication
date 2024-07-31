package com.example.ExpenseSharingApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ExpenseSharingApp.Models.ParticipantShare;

@Repository
public interface ParticipantShareRepo extends JpaRepository<ParticipantShare, Long> {
    
}
