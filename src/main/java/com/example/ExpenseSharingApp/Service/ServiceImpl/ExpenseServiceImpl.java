package com.example.ExpenseSharingApp.Service.ServiceImpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ExpenseSharingApp.Dto.ExpenseDto;
import com.example.ExpenseSharingApp.Dto.ParticipantDto;
import com.example.ExpenseSharingApp.Exceptions.ExpenseException;
import com.example.ExpenseSharingApp.Models.Expense;
import com.example.ExpenseSharingApp.Models.Participant;
import com.example.ExpenseSharingApp.Models.User;
import com.example.ExpenseSharingApp.Repository.ExpenseRepository;
import com.example.ExpenseSharingApp.Repository.ParticipantRepository;
import com.example.ExpenseSharingApp.Repository.UserRepository;
import com.example.ExpenseSharingApp.Service.ExpenseService;


@Service
public class ExpenseServiceImpl implements ExpenseService {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto, Integer creatorId) throws ExpenseException {
           
        Expense expense = modelMapper.map(expenseDto,Expense.class);
         Optional<User> optional=userRepository.findById(creatorId);
          if(optional.isEmpty()){
              throw new ExpenseException("User does not exist !!");
          }
          User user=optional.get();
          expense.setCreatedBy(user);
          expense.setCreatedAt(new Timestamp(System.currentTimeMillis()));
         Expense savedExpense=expenseRepository.save(expense);
          if (validate(expenseDto.getParticipants()).compareTo(expense.getTotalAmount())!=0) {
              throw new ExpenseException("Total participant amount does not match the expense total amount");
          }

         List<Participant> participants = new ArrayList<>();
         List<ParticipantDto> participantDto1=new ArrayList<>();
         if (expenseDto.getParticipants() != null) {
             for (ParticipantDto participantDto : expenseDto.getParticipants()) {
                 Participant participant = modelMapper.map(participantDto, Participant.class);
                
                 // Find the user associated with the participant
                 Optional<User> participantUserOptional = userRepository.findById(participantDto.getPaidBy().getUserId());
                 if (participantUserOptional.isEmpty()) {
                     throw new ExpenseException("Participant user does not exist!!");
                 }
                 User participantUser = participantUserOptional.get();
 
                 // Set the user and expense for the participant
                 participant.setPaidBy(participantUser);
                 participant.setExpense(savedExpense);
                 participants.add(participant);
             }
            participantDto1=participantRepository.saveAll(participants).stream().map(part->modelMapper.map(part,ParticipantDto.class)).collect(Collectors.toList());

            
    }
        ExpenseDto expenseDto2=modelMapper.map(savedExpense, ExpenseDto.class);
        expenseDto2.setParticipants(participantDto1);
        return  expenseDto2;
}

    public BigDecimal validate(List<ParticipantDto> splits) {
        BigDecimal total = BigDecimal.ZERO;
        for (ParticipantDto split : splits) {
            total = total.add(split.getAmount());
        }
        return total;
    }

    @Override
    public ExpenseDto getExpenseById(Long id) throws ExpenseException {
        
          Optional<Expense> optional=expenseRepository.findById(id);

          if(optional.isEmpty()){
              throw new ExpenseException("Expense does not exist");
          }
           Expense expense=optional.get();

           return modelMapper.map(expense,ExpenseDto.class);
    }

    @Override
    public List<ExpenseDto> getAllExpense() throws ExpenseException {
        
          List<Expense> expenses=expenseRepository.findAll();

         return expenses.stream().map(expense->modelMapper.map(expense,ExpenseDto.class)).collect(Collectors.toList());

    }

    public List<ParticipantDto> getExpensesByUser(Integer userId) {
        return participantRepository.findAll().stream()
                        .filter(split -> split.getPaidBy().getUserId()==userId)
                        .map(participant->modelMapper.map(participant,ParticipantDto.class))
                        .collect(Collectors.toList());
                        
    }
    
}
