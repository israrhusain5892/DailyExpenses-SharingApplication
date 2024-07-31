package com.example.ExpenseSharingApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;

@SpringBootApplication
public class ExpenseSharingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseSharingAppApplication.class, args);
	}
	
	@Bean
    public ModelMapper modelMapper(){
		 return new ModelMapper();
	}
}
