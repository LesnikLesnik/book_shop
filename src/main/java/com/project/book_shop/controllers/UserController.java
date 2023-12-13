package com.project.book_shop.controllers;

import com.project.book_shop.dto.SignInDto;
import com.project.book_shop.dto.SignUpDto;
import com.project.book_shop.entity.User;
import com.project.book_shop.entity.enums.Role;
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

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        User newUser = new User();
        newUser.setLogin(signUpDto.getLogin());
        newUser.setEmail(signUpDto.getEmail());
        newUser.setPassword(signUpDto.getPassword());
        newUser.setRoles(Collections.singleton(Role.GUEST));

        userService.register(newUser);
        return ResponseEntity.ok().build();
    }

}
