package com.example.ExpenseSharingApp.Service.ServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ExpenseSharingApp.Dto.UserDto;
import com.example.ExpenseSharingApp.Exceptions.UserException;
import com.example.ExpenseSharingApp.Models.Role;
import com.example.ExpenseSharingApp.Models.User;
import com.example.ExpenseSharingApp.Repository.RoleRepository;
import com.example.ExpenseSharingApp.Repository.UserRepository;
import com.example.ExpenseSharingApp.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto userDto) throws UserException {

        Optional<User> registeredUser = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (registeredUser.isPresent())throw new UserException("User is already registered!");

        Optional<Role> role = roleRepository.findById(2);
        if (role.isEmpty())
            throw new UserException("User Role not found");
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<Role> roles = user.getRoles();
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role.get());
        user.setRoles(roles);
        System.out.println(role);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUser(Integer id) throws UserException {

        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty())
            throw new UserException("User does not exist");

        User user = optional.get();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    @Override
    public List<UserDto> getUsers() {

        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public String deleteUser(Integer id) throws UserException {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty())
            throw new UserException("User does not exist");

        User user = optional.get();
        userRepository.delete(user);

        return "User deleted successfully !!";
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) throws UserException {

        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty())
            throw new UserException("User does not exist");

        User user = optional.get();
        user.setEmail(userDto.getEmail());
        user.setMobileNumber(userDto.getMobileNumber());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(userDto.getRoles());
        return modelMapper.map(user, UserDto.class);
    }

}
