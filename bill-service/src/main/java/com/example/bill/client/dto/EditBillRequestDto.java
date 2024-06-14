package com.example.bill.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EditBillRequestDto {

    private UUID accountId;

    private UUID billId;
}
