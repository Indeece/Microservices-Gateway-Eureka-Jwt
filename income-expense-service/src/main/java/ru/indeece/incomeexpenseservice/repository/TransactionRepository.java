package ru.indeece.incomeexpenseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.indeece.incomeexpenseservice.model.Transactions;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    @Query(value = "SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE user_id = :userId AND type = :type", nativeQuery = true)
    BigDecimal sumAmountByUserIdAndType(@Param("userId") Long userId, @Param("type") String type);
}