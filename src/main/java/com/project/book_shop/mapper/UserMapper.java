package com.project.book_shop.mapper;

import com.project.book_shop.dto.SignUpDto;
import com.project.book_shop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import com.project.book_shop.entity.enums.Role;

@Mapper
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(signUpDto.getPassword()))")
    @Mapping(target = "login", source = "signUpDto.login")
    @Mapping(target = "email", source = "signUpDto.email")
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "roles", expression = "java(defaultRoles())")
    public abstract User signUpDtoToUser(SignUpDto signUpDto);


    protected Set<Role> defaultRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.GUEST); // Назначаем роль GUEST по умолчанию (по ТЗ)
        return roles;
    }
}
