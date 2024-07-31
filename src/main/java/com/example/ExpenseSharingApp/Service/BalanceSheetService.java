
package com.example.ExpenseSharingApp.Service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ExpenseSharingApp.Exceptions.ExpenseException;
import com.example.ExpenseSharingApp.Models.Expense;
import com.example.ExpenseSharingApp.Models.Participant;
import com.example.ExpenseSharingApp.Models.User;
import com.example.ExpenseSharingApp.Repository.ExpenseRepository;
import com.example.ExpenseSharingApp.Repository.ParticipantRepository;
import com.example.ExpenseSharingApp.Repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class BalanceSheetService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserRepository userRepository;
    
 public static String generateCsvBalanceSheet(User user, List<Participant> splits) throws IOException {
        String csvFile = "balance_sheet_" + user.getUserId() + ".csv";
        try (FileOutputStream fos = new FileOutputStream(csvFile);
             Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Balance Sheet");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Expense Description", "Total Amount", "User", "Amount Owed"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }
            int rowNum = 1;
            for (Participant split : splits) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(split.getExpense().getExpenseName());
                row.createCell(1).setCellValue(split.getExpense().getTotalAmount().doubleValue());
                row.createCell(2).setCellValue(split.getPaidBy().getName());
                row.createCell(3).setCellValue(split.getAmount().doubleValue());
            }
            // for (PercentageSplit percentageSplit : percentageSplits) {
            //     Row row = sheet.createRow(rowNum++);
            //     row.createCell(0).setCellValue(percentageSplit.getExpense().getDescription());
            //     row.createCell(1).setCellValue(percentageSplit.getExpense().getTotalAmount().doubleValue());
            //     row.createCell(2).setCellValue(percentageSplit.getUser().getName());
            //     row.createCell(3).setCellValue(percentageSplit.getExpense().getTotalAmount()
            //             .multiply(percentageSplit.getPercentage()).divide(BigDecimal.valueOf(100)).doubleValue());
            // }
            workbook.write(fos);
        }
        return csvFile;
    }
}