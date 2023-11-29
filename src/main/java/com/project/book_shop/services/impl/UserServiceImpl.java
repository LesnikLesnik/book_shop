package com.project.book_shop.services.impl;

import com.project.book_shop.entity.User;
import com.project.book_shop.repositories.UserRepository;
import com.project.book_shop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + username));
    }

    public void register(User user) {
        if (loadUserByUsername(user.getLogin()) != null) {
            throw new RuntimeException("User already exists with this login or email");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = (User) loadUserByUsername(login);
        if (user != null) {
            return user.getLogin().equals(login) && user.getPassword().equals(password);
        }
        return false;
    }
}
