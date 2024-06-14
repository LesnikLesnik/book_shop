package com.example.bill.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddBillRequestDto {

    private UUID accountId;

    private UUID billId;
}
