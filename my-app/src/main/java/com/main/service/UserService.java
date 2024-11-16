package com.main.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.UserEntity;
import com.main.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity saveUser(UserEntity user) {
        return userRepo.save(user);
    }

    public List<UserEntity> getUserByUserName(String username) {
        return userRepo.findUserWithUserName(username);
    }

    public Page<UserEntity> getUserPaging(Integer page, Integer size) {
        Sort sort = Sort.by("username").descending().and(Sort.by("user_id").ascending());
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepo.findAll(pageable);
    }

}
