package com.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.main.transaction.enums.TransactionType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@Entity(name = "secure_transaction")
public class SecureTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "transaction_type")
    private TransactionType transactionType;
}
