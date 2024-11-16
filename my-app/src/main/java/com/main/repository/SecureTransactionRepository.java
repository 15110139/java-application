package com.main.repository;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.main.entity.SecureTransactionEntity;


public interface SecureTransactionRepository extends PagingAndSortingRepository<SecureTransactionEntity,Long> {
}


class CustomerRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    //Build root entity

    public List<SecureTransactionEntity> findAllByCondition( List<Predicate> predicateList) throws ParseException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SecureTransactionEntity> criteriaQuery = builder.createQuery(SecureTransactionEntity.class);
        criteriaQuery.where(builder.and(predicateList.toArray(new Predicate[0])));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public  Root<SecureTransactionEntity> buildRootEntity(){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SecureTransactionEntity> criteriaQuery = builder.createQuery(SecureTransactionEntity.class);
        return criteriaQuery.from(SecureTransactionEntity.class);
    }
}

