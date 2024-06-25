package com.example.bill.integration;

import com.example.bill.BillApplication;
import com.example.bill.dto.BillRequestDto;
import com.example.bill.dto.BillResponseDto;
import com.example.bill.entity.Bill;
import com.example.bill.repository.BillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {BillApplication.class, TestConfig.class, TestContainerConfig.class})
public class BillControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BillRepository billRepository;


    @Test
    public void getBillById_shouldReturnBill() {
        UUID billId = UUID.randomUUID();
        Bill bill = new Bill(billId, UUID.randomUUID(), "test@example.com", new BigDecimal("100.00"));
        billRepository.save(bill);

        ResponseEntity<BillResponseDto> response = restTemplate.getForEntity("/api/bills/" + billId, BillResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(billId, response.getBody().getId());
    }

    @Test
    public void deposit_shouldUpdateBillAmount() {
        UUID billId = UUID.randomUUID();
        Bill bill = new Bill(billId, UUID.randomUUID(), "test@example.com", new BigDecimal("100.00"));
        billRepository.save(bill);

        ResponseEntity<BillResponseDto> response = restTemplate.exchange("/api/bills/" + billId + "?deposit=50", HttpMethod.PUT, null, BillResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("150.00"), response.getBody().getAmount());
    }

}
