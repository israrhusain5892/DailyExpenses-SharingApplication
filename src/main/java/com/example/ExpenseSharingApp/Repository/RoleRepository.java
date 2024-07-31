package com.example.ExpenseSharingApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ExpenseSharingApp.Models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    
}
