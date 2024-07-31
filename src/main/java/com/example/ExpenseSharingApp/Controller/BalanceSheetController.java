package com.example.ExpenseSharingApp.Controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExpenseSharingApp.Exceptions.ExpenseException;
import com.example.ExpenseSharingApp.Models.Expense;
import com.example.ExpenseSharingApp.Models.Participant;
import com.example.ExpenseSharingApp.Models.User;
import com.example.ExpenseSharingApp.Repository.ExpenseRepository;
import com.example.ExpenseSharingApp.Repository.ParticipantRepository;
import com.example.ExpenseSharingApp.Repository.UserRepository;
import com.example.ExpenseSharingApp.Service.BalanceSheetService;
import com.example.ExpenseSharingApp.Service.ServiceImpl.ExpenseServiceImpl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/public")
public class BalanceSheetController {
   
    @Autowired
    private BalanceSheetService expenseService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

  @GetMapping("/{expense_id}")
    public ResponseEntity<InputStreamResource> downloadBalanceSheet(@PathVariable Long expense_id,
                                                                    @RequestParam String format) throws IOException {
        Expense expense = expenseRepository.findById(expense_id).get();
        User user=expense.getCreatedBy();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Participant> participants = participantRepository.findByExpense(expense);
        // List<PercentageSplit> percentageSplits = expenseService.getAllPercentageSplitsByUserId(userId);

        String filePath = "";
        if ("csv".equalsIgnoreCase(format)) {
            filePath = expenseService.generateCsvBalanceSheet(user, participants);
        }
        //  else if ("pdf".equalsIgnoreCase(format)) {
        //     filePath = expenseService.generatePdfBalanceSheet(user, participants);
        // } 
        else {
            return ResponseEntity.badRequest().body(null);
        }

        FileInputStream fis = new FileInputStream(filePath);
        InputStreamResource resource = new InputStreamResource(fis);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + filePath);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType("csv".equalsIgnoreCase(format) ? MediaType.parseMediaType("text/csv") : MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
