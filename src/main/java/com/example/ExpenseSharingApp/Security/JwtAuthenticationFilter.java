package com.example.ExpenseSharingApp.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

     @Autowired
    private CustomUserDetailService userDetailsService;

    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


//        get token
        String requestToken=request.getHeader("Authorization");
        log.info("Header : {}", requestToken);

        String username=null;
        String token=null;

        if( requestToken!=null && requestToken.startsWith("Bearer")){
              token=requestToken.substring(7);
              try{
                  username=this.jwtTokenHelper.getUserNameFromToken(token);
              }
              catch(IllegalArgumentException e){
                   System.out.println("Unable to get jwt token");
              }
              catch(ExpiredJwtException e){
                  System.out.println("jwt token has expired");
              }
              catch(MalformedJwtException e){
                  System.out.println("Invalid jwt exception");
              }

        }

        else{
            System.out.println("jwt token does not begin with Bearer");
        }

     // validate token now
      if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

             UserDetails userDetails=userDetailsService.loadUserByUsername(username);
             if(this.jwtTokenHelper.validateToken(token,userDetails)){
                 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
             }
             else{
                 System.out.println("Invalid token");
             }
      }
      else{
          System.out.println("username is null");
      }

      filterChain.doFilter(request,response);
    }
}
