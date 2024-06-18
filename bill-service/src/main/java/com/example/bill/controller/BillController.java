package com.example.bill.controller;

import com.example.bill.dto.BillRequestDto;
import com.example.bill.dto.BillResponseDto;
import com.example.bill.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    @PostMapping("/")
    public BillResponseDto create(@RequestBody BillRequestDto billRequestDto){
        return billService.createBill(billRequestDto.getAccountId(), billRequestDto.getAmount());
    }

    @GetMapping("/{id}")
    public BillResponseDto getBillById(@PathVariable UUID id){
        return billService.getBillById(id);
    }
    @PutMapping("/{id}")
    public BillResponseDto deposit(@PathVariable UUID id, @RequestParam BigDecimal deposit){
        return billService.deposit(id, deposit);
    }

    @PutMapping("/{billId}/buyBook/{bookId}")
    public BillResponseDto buyBook(@PathVariable UUID billId, @PathVariable UUID bookId) {
        return billService.buyBook(billId, bookId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        billService.deleteBill(id);
    }
}
