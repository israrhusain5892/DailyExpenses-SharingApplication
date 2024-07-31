package com.example.ExpenseSharingApp.Dto;

import java.util.Set;

import com.example.ExpenseSharingApp.Models.Role;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
       
     private Integer userId;

    @NotBlank(message="username is mandatory")
    private String name;

    @NotBlank(message="mobile is mandatory")
    @Size(min=10,message = "mobile should be contain 10 digits ")
    private String mobileNumber;

    @Column(unique = true)
    @NotBlank(message="Email is mandatory")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}"
     ,message="email is not valid"
    )
    private String email;


    @NotBlank(message="password is required")
    private String password;

    private Set<Role> roles;
}
