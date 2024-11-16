package com.main.repository.specitifications;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

import com.main.entity.SecureTransactionEntity;
import com.main.transaction.enums.TransactionStatus;
import com.main.transaction.enums.TransactionType;

import org.springframework.data.jpa.domain.Specification;

public class SecureTransactionSpecifications {

    public static Specification<SecureTransactionEntity> hasTransactionId(String secureTransactionId) {
        return (root, query, criterialBuilder) -> criterialBuilder.equal(root.get("id"), secureTransactionId);
    }

    public static Specification<SecureTransactionEntity> hasType(TransactionType type) {
        return (root, query, criterialBuilder) -> criterialBuilder.equal(root.get("transaction_type"), type);
    }

    public static Specification<SecureTransactionEntity> hasStatus(TransactionStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("transaction_status"), status);
    }

    public static Specification<SecureTransactionEntity> hasTypeIn(List<TransactionType> types) {
        return (root, query, criteriaBuilder) -> {
            if (types != null && !types.isEmpty()) {
                return root.get("transaction_status").in(types);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<SecureTransactionEntity> createSpecification(String secureTransactionId,
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
