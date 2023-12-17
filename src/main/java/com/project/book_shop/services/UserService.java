package com.project.book_shop.services;

import com.project.book_shop.entity.User;
import com.project.book_shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Lazy
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + username));
    }

    public void register(User user) {
        userRepository.findByLogin(user.getLogin()).ifPresent(u -> {
            throw new RuntimeException("Кто-то уже использует этот логин: " + user.getLogin());
        });
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Кто-то уже использует этот email: " + user.getEmail());
        });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
