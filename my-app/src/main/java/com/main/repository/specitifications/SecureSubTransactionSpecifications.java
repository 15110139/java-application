package com.main.repository.specitifications;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

import com.main.entity.SecureSubTransactionEntity;
import com.main.transaction.enums.TransactionStatus;
import com.main.transaction.enums.TransactionType;

import org.springframework.data.jpa.domain.Specification;

public class SecureSubTransactionSpecifications {
    private Specification<SecureSubTransactionEntity> spec;

    public SecureSubTransactionSpecifications() {
        this.spec = Specification.where(null);
    }

    public SecureSubTransactionSpecifications withTransactionId(String secureTransactionId) {
        this.spec = this.spec.and(SecureSubTransactionSpecifications.hasTransactionId(secureTransactionId));
        return this;
    }

    public SecureSubTransactionSpecifications withType(TransactionType type) {
        this.spec = this.spec.and(SecureSubTransactionSpecifications.hasType(type));
        return this;
    }

    public SecureSubTransactionSpecifications withStatus(TransactionStatus status) {
        this.spec = this.spec.and(SecureSubTransactionSpecifications.hasStatus(status));
        return this;
    }

    public SecureSubTransactionSpecifications withStatusIn(List<TransactionStatus> status) {
        this.spec = this.spec.and(SecureSubTransactionSpecifications.hasStatusIn(status));
        return this;
    }

    public SecureSubTransactionSpecifications withTypeIn(List<TransactionType> types) {
        this.spec = this.spec.and(SecureSubTransactionSpecifications.hasTypeIn(types));
        return this;
    }

    public Specification<SecureSubTransactionEntity> build() {
        return this.spec;
    }

    public static Specification<SecureSubTransactionEntity> hasTransactionId(String secureTransactionId) {
        return (root, query, criterialBuilder) -> criterialBuilder.equal(root.get("id"), secureTransactionId);
    }

    public static Specification<SecureSubTransactionEntity> hasType(TransactionType type) {
        return (root, query, criterialBuilder) -> criterialBuilder.equal(root.get("type"), type);
    }

    public static Specification<SecureSubTransactionEntity> hasStatus(TransactionStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<SecureSubTransactionEntity> hasTypeIn(List<TransactionType> types) {
        return (root, query, criteriaBuilder) -> {
            if (types != null && !types.isEmpty()) {
                return root.get("type").in(types);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<SecureSubTransactionEntity> hasStatusIn(List<TransactionStatus> status) {
        return (root, query, criteriaBuilder) -> {
            if (status != null && !status.isEmpty()) {
                return root.get("status").in(status);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<SecureSubTransactionEntity> createSpecification(String secureTransactionId,
            TransactionStatus status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (secureTransactionId != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), secureTransactionId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
