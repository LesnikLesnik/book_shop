package com.example.bill.controller;

import com.example.bill.dto.BillRequestDto;
import com.example.bill.dto.BillResponseDto;
import com.example.bill.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    @PostMapping("/")
    public UUID create(BillRequestDto billRequestDto){
        return billService.createBill(billRequestDto);
    }

//    public BillResponseDto getBillById(UUID id){
//        return billService.getBillById(id);
//    }
//
//    public BillResponseDto deposit(UUID id){
//        return billService.deposit(id);
//    }
//
//    public void delete(UUID id){
//        billService.deleteBill(id);
//    }
}
