package com.example.bill.repository;

import com.example.bill.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {
}
