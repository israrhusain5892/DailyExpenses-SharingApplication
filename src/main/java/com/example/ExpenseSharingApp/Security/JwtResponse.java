package com.example.ExpenseSharingApp.Security;

import com.example.ExpenseSharingApp.Dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class JwtResponse {

       private String token;
       private UserDto userDto;
}
