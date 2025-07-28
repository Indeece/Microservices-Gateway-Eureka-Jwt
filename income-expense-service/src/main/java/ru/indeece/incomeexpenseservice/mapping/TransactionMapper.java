package ru.indeece.incomeexpenseservice.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.indeece.incomeexpenseservice.dto.TransactionsDto;
import ru.indeece.incomeexpenseservice.model.Transactions;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    TransactionsDto toDto(Transactions transaction);
    Transactions toEntity(TransactionsDto transactionsDto);
}