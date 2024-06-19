package com.example.bill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class BillResponseDtoToRabbit {


    private UUID id;

    private String email;

    private BigDecimal deposit;
}
