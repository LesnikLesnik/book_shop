package com.example.bill.client.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class BookForSaleResponseDto {

    private UUID id;

    private Integer cost;
}
