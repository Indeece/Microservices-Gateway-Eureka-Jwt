package ru.indeece.incomeexpenseservice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.indeece.incomeexpenseservice.dto.TransactionsDto;
import ru.indeece.incomeexpenseservice.enums.TransactionType;
import ru.indeece.incomeexpenseservice.model.Transactions;
import ru.indeece.incomeexpenseservice.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionsDto addTrasactions(TransactionsDto transactionsDto) {
        Long userId = getAuthUserId();
        Transactions transaction = toEntity(transactionsDto, userId);
        Transactions saved = repository.save(transaction);
        return toDto(saved);
    }

    public Map<String, BigDecimal> getTransactionSummary(TransactionType type) {
        Map<String, BigDecimal> summary = new HashMap<>();
        if (type != null) {
            BigDecimal total =  repository.sumAmountByUserIdAndType(getAuthUserId(), type.name());
            summary.put(type == TransactionType.INCOME ? "totalIncome" : "totalExpense", total);
        } else {
            BigDecimal totalIncome =  repository.sumAmountByUserIdAndType(getAuthUserId(), TransactionType.INCOME.name());
            BigDecimal totalExpense =  repository.sumAmountByUserIdAndType(getAuthUserId(), TransactionType.EXPENSE.name());

            BigDecimal sumTotal = totalIncome.subtract(totalExpense);

            summary.put("totalIncome", totalIncome);
            summary.put("totalExpense", totalExpense);
            summary.put("sumTotal", sumTotal);
        }

        return summary;
    }

    private Long getAuthUserId(){
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userId);
        return userId;
    }

    public List<TransactionsDto> getAllTransactions() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private TransactionsDto toDto(Transactions entity) {
        if (entity == null) return null;

        TransactionsDto dto = new TransactionsDto();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        dto.setTransactionDate(entity.getTransactionDate());
        return dto;
    }

    private Transactions toEntity(TransactionsDto dto, Long userId) {
        if (dto == null) return null;

        Transactions entity = new Transactions();
        entity.setUserId(userId);
        entity.setAmount(dto.getAmount());
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setTransactionDate(dto.getTransactionDate());
        return entity;
    }
}