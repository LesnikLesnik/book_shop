package com.example.account.controller.annotations;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Операция успешно выполнена"),
        @ApiResponse(responseCode = "400", description = "Неверный запрос"),
        @ApiResponse(responseCode = "401", description = "Ошибка авторизации"),
        @ApiResponse(responseCode = "412", description = "Отсутствие ресурса"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера"),
        @ApiResponse(responseCode = "503")
})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DefaultApiResponses {

}
