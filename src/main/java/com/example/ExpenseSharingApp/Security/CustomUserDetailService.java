package com.example.ExpenseSharingApp.Security;




import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ExpenseSharingApp.Models.User;
import com.example.ExpenseSharingApp.Repository.UserRepository;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("no user found");
        }

        // logger.info("User found: " + user.getUsername());
        // user.getRoles().forEach(role -> log.info("Role: " + role.getName()));

        return user;
        
    }
}
