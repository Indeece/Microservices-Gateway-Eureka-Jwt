package ru.indeece.incomeexpenseservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TransactionType {
    INCOME,
    EXPENSE;

    @JsonCreator
    public static TransactionType fromString(String value){
        for (TransactionType type: TransactionType.values()){
            if (type.name().equalsIgnoreCase(value)){
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown Enum type");
    }
}
