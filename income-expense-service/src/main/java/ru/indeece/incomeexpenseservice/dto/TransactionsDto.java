package ru.indeece.incomeexpenseservice.dto;

import lombok.*;
import ru.indeece.incomeexpenseservice.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsDto {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private LocalDate transactionDate;
}