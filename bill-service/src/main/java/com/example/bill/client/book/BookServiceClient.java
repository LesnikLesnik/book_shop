package com.example.bill.client.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "book-service")
public interface BookServiceClient {


    @GetMapping("/api/books/sell/{id}")
    BookForSaleResponseDto getBookForSale(@PathVariable UUID id);
}
