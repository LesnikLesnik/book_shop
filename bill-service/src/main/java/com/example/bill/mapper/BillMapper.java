package com.example.bill.mapper;

import com.example.bill.dto.BillRequestDto;
import com.example.bill.dto.BillResponseDto;
import com.example.bill.entity.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BillMapper {

    @Mapping(target = "id", ignore = true)
    Bill toBill(BillRequestDto billRequestDto);

    BillResponseDto toResponse(Bill bill);
}
