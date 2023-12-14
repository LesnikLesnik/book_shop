package com.project.book_shop.controllers;

import com.project.book_shop.dto.userDto.SignUpDto;
import com.project.book_shop.entity.User;
import com.project.book_shop.entity.enums.Role;
import com.project.book_shop.mapper.UserMapper;
import com.project.book_shop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/signUp")
    public ResponseEntity<Void> registerUser(@RequestBody SignUpDto signUpDto) {
        User newUser = userMapper.signUpDtoToUser(signUpDto);
        userService.register(newUser);
        return ResponseEntity.ok().build();
    }

}
