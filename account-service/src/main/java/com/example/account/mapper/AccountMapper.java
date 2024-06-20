package com.example.account.mapper;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "books", expression = "java(null)")
    @Mapping(target = "billId", expression = "java(null)")
    @Mapping(target = "creationDate", expression = "java(new java.util.Date())")
    Account toAccount(AccountRequestDto accountRequestDto);

    AccountResponseDto toResponseDto(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    void updateAccountFromDto(AccountRequestDto dto, @MappingTarget Account account);
}
