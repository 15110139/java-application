package com.main.controller;

import java.util.List;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.dto.CreateUserRequest;
import com.main.dto.GetUserRequest;
import com.main.entity.UserEntity;
import com.main.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public  Page<UserEntity> getUser(@RequestBody GetUserRequest getUserRequest) {
        Page<UserEntity> user = userService.getUserPaging(getUserRequest.getPage(), getUserRequest.getSize());
        Stream<UserEntity> userSteam = user.getContent().stream();
        userSteam.forEach(userDetail -> System.out.println(userDetail.getEmail()));
        return user;
    }

    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserRequest createUser) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail("email@example.com");
        newUser.setPassword("password");
        newUser.setUserName("username");
        newUser.setUserId(("userid"));
        userService.saveUser(newUser);
        return ResponseEntity.ok("User created successfully");
    }

}
