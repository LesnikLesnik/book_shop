package com.example.notification.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class BillResponseDtoToRabbit {

    private UUID id;

    private String email;

    private BigDecimal deposit;
}
