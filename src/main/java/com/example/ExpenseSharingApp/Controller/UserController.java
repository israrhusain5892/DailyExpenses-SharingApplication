package com.example.ExpenseSharingApp.Controller;


    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

import com.example.ExpenseSharingApp.Dto.UserDto;
import com.example.ExpenseSharingApp.Exceptions.UserException;
import com.example.ExpenseSharingApp.Service.UserService;
import com.example.ExpenseSharingApp.Service.ServiceImpl.UserServiceImpl;

import java.util.List;
    
    @RestController
    @CrossOrigin(origins = "*")
    // @RequestMapping("/")
    public class UserController {
    
        @Autowired
        private UserServiceImpl userService;
       
    
        @PostMapping("/public/user/register")
        public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto user) throws UserException {
            UserDto savedUser = userService.createUser(user);
            return new ResponseEntity<UserDto>(savedUser, HttpStatus.CREATED);
        }
    
        @PutMapping("/admin/user/update/{id}")
        public  ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto user, @PathVariable Integer id) throws UserException {
            UserDto updatedUser = userService.updateUser(user, id);
            return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
        }
    
        @DeleteMapping("/admin/user/delete/{userId}")
        public  ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId) throws UserException{
            String deletedUser= userService.deleteUser(userId);
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        }
    
        @GetMapping("/user/{userId}")
        public  ResponseEntity<UserDto> viewUserById(@PathVariable("userId") Integer userId) throws UserException{
            UserDto user= userService.getUser(userId);
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        }
    
        @GetMapping("/admin/user/all")
        public  ResponseEntity<List<UserDto>> viewAllUsers() throws UserException {
            List<UserDto> list = userService.getUsers();
            return new ResponseEntity<List<UserDto>>(list, HttpStatus.OK);
    
        }
    
    }
    

