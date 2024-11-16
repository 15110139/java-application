package com.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.main.entity.UserEntity;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>  {
    UserEntity findByUserName(@Param("username") String username);

    @Query(value = "select * from users u where u.username = :userName",nativeQuery = true)
    List<UserEntity> findUserWithUserName(@Param("userName") String userName);
    
    Page<UserEntity> findAll(Pageable pageable);
}
