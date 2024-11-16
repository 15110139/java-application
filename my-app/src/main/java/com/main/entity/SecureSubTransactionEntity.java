package com.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.main.transaction.enums.TransactionStatus;
import com.main.transaction.enums.TransactionType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity(name = "secure_sub_transaction")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class SecureSubTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private String id;

    @Column(name = "secure_transaction_id")
    private String secureTransactionId;

    @Column(name = "type")
    private TransactionType type;

    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "request_id")
    private String requestId;

    @Type(type = "jsonb")
    @Column(name = "payload", columnDefinition = "jsonb")
    private Object payload;
}
