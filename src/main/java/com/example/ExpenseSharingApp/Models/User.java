package com.example.ExpenseSharingApp.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

//    @NotBlank(message="password is required")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$"
//      ,message="password should contain at least one Capital Letter, one small , one digit and" +
//            "one special character"
//    )
    @NotBlank(message="password is required")
    private String password;


   

    @ManyToMany(cascade = {CascadeType.ALL,CascadeType.REMOVE},fetch = FetchType.EAGER )
    @JoinTable(name="user_role",
             joinColumns = {@JoinColumn(name="USER",referencedColumnName = "userId")},
            inverseJoinColumns ={@JoinColumn(name="ROLE",referencedColumnName = "id")})
    private Set<Role> roles=new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        List<SimpleGrantedAuthority> authorities= this.roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
         return authorities;
        
    }

    @Override
    public String getUsername() {

        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
       
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
       
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
       
        return true;
    }

    @Override
    public boolean isEnabled() {
       
        return true;
    }

    
   
}

