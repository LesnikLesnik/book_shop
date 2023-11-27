package com.project.book_shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    // security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>(); // Пока что оставим пустым, в дальнейшем можно добавить роли
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Можно изменить логику в зависимости от потребностей проекта
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Аналогично isAccountNonExpired
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Аналогично isAccountNonExpired
    }

    @Override
    public boolean isEnabled() {
        return true; // Аналогично isAccountNonExpired
    }
}
