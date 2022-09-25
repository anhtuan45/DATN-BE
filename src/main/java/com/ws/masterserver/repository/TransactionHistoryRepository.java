package com.ws.masterserver.repository;

import com.ws.masterserver.entity.TransactionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity,String> {

    @Query(value = "SELECT * FROM transaction_history t WHERE t.transaction_no = ?1",nativeQuery = true)
    Optional<TransactionHistoryEntity> findByTransactionNo(String transactionNo);
}

