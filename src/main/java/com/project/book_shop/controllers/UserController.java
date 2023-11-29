package com.project.book_shop.controllers;

import com.project.book_shop.dto.SignInDto;
import com.project.book_shop.dto.SignUpDto;
import com.project.book_shop.entity.User;
import com.project.book_shop.entity.enums.Role;
import com.project.book_shop.service.UserService;
import com.project.book_shop.services.impl.UserServiceImpl;
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

    private final UserServiceImpl userService;

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

    //TODO: разобраться с необходимостью реализации метода authenticate
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInDto signInDto) {
        boolean isAuthenticated = userService.authenticate(signInDto.getLogin(), signInDto.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
