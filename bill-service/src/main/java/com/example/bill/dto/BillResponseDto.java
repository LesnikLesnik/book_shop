package com.example.bill.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class BillResponseDto {

    private UUID id;

    private UUID accountId;

    private BigDecimal amount;
}
