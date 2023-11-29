package com.project.book_shop.services;

import com.project.book_shop.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
   UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void register(User user);

    boolean authenticate(String login, String password);
}
