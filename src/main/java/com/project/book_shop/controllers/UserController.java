package com.project.book_shop.controllers;

import com.project.book_shop.dto.userDto.SignInDto;
import com.project.book_shop.dto.userDto.SignUpDto;
import com.project.book_shop.entity.User;
import com.project.book_shop.mapper.UserMapper;
import com.project.book_shop.security.jms.EmailService;
import com.project.book_shop.security.jwt.JwtUtils;
import com.project.book_shop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody SignUpDto signUpDto) {
        User newUser = userMapper.signUpDtoToUser(signUpDto);
        UUID token = UUID.randomUUID();
        userService.register(newUser, token);
        emailService.sendRegistrationMessage(newUser.getEmail(), token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/{token}")
    public ResponseEntity<Void> confirmRegistration(@PathVariable UUID token) {
        userService.activateUser(token);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/signIn")
    public ResponseEntity<String> authenticateUser(@RequestBody SignInDto signInDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.getLogin(), signInDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication.getName());

        return ResponseEntity.ok(jwt);
    }

}
