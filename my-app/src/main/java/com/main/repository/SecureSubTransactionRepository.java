package com.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.TransactionStatus;

import com.main.entity.SecureSubTransactionEntity;
import com.main.transaction.enums.TransactionType;

public interface SecureSubTransactionRepository extends PagingAndSortingRepository<SecureSubTransactionEntity, String>,
                JpaSpecificationExecutor<SecureSubTransactionEntity> {

        Optional<SecureSubTransactionEntity> findBySecureTransactionIdAndStatusAndType(
                        @Param("secureTransactionId") String securTransactionId,
                        @Param("type") TransactionType type,
                        @Param("status") TransactionStatus status);

        Optional<SecureSubTransactionEntity> findBySecureTransactionIdAndStatusAndTypeAndRequestId(
                        @Param("secureTransactionId") String securTransactionId,
                        @Param("type") TransactionType type,
                        @Param("status") TransactionStatus status,
                        @Param("requestId") String requestId);
}
