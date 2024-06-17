package com.example.account.repository;

import com.example.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface AccountRepository extends JpaRepository<Account, UUID> {
}
