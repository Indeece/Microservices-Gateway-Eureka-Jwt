package ru.indeece.incomeexpenseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.indeece.incomeexpenseservice.dto.TransactionsDto;
import ru.indeece.incomeexpenseservice.enums.TransactionType;
import ru.indeece.incomeexpenseservice.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<TransactionsDto> addTransaction(@RequestBody TransactionsDto transactionsDto) {
        TransactionsDto savedTransaction = transactionService.addTrasactions(transactionsDto);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping
    public ResponseEntity<List<TransactionsDto>> getTransactions(){
        var transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/summary")
    public ResponseEntity<Map<String, BigDecimal>> getTransactionSummary(@RequestParam(required = false) String type){
        TransactionType transactionType = type != null ? TransactionType.fromString(type) : null;

        Map<String, BigDecimal> summary = transactionService.getTransactionSummary(transactionType);
        return ResponseEntity.ok(summary);
    }
}