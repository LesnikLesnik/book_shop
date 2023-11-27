package com.project.book_shop.controllers;

import com.project.book_shop.dto.SignInDto;
import com.project.book_shop.dto.SignUpDto;
import com.project.book_shop.entity.User;
import com.project.book_shop.entity.enums.Role;
import com.project.book_shop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        User newUser = new User();
        newUser.setLogin(signUpDto.getLogin());
        newUser.setEmail(signUpDto.getEmail());
        newUser.setPassword(signUpDto.getPassword()); // Пароль будет хеширован в сервисе
        newUser.setRoles(Collections.singleton(Role.GUEST)); // Назначаем роль GUEST

        userService.register(newUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInDto signInDto) {
        // Логика аутентификации (предположим, что это будет делаться в userService)
        boolean isAuthenticated = userService.authenticate(signInDto.getLogin(), signInDto.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
