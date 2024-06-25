package com.example.bill.integration.client.book;

import com.example.bill.сlient.book.BookForSaleResponseDto;
import com.example.bill.сlient.book.BookServiceClient;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookServiceClientStub implements BookServiceClient {

    @Override
    public BookForSaleResponseDto getBookForSale(UUID id) {
        return new BookForSaleResponseDto(id, 100);
    }
}
