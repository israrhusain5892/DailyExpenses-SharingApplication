package com.example.ExpenseSharingApp.Service;

import java.util.List;

import com.example.ExpenseSharingApp.Dto.UserDto;
import com.example.ExpenseSharingApp.Exceptions.UserException;

public interface UserService {
   
    UserDto createUser(UserDto userDto) throws UserException;
    UserDto getUser(Integer id) throws UserException;
    List<UserDto> getUsers() throws UserException;

    String deleteUser(Integer id) throws UserException;

    UserDto updateUser(UserDto userDto,Integer id) throws UserException;
    
}
