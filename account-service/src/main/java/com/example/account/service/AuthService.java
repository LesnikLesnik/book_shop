package com.example.account.service;

import com.example.account.dto.AccountRequestDto;
import com.example.account.entity.AuthResponse;
import com.example.account.entity.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtils jwtUtils;

    public AuthResponse register(AccountRequestDto request){
        //do validation if user exists in DB
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        UserVO registeredUser = restTemplate.postForObject("http/account-service/users", request, UserVO.class);

        String accessToken = jwtUtils.generate(registeredUser.getId(), registeredUser.getRole(), "ACCESS");
        String refreshToken = jwtUtils.generate(registeredUser.getId(), registeredUser.getRole(), "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }
}
