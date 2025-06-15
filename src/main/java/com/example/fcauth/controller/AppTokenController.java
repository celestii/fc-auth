package com.example.fcauth.controller;

import com.example.fcauth.dto.AppTokenRespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/token")
@Tag(name="App2App Token", description = "app2app token API")
public class AppTokenController {
    private final TokenService tokenService;

    @Operation(description = "토큰 발급")
    @PostMapping(value = "/new/{appId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppTokenRespDto> createNewAppToken(@PathVariable Long appId){
        AppTokenRespDto dto = tokenService.createAppToken(appId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(description = "토큰 밸리데이션")
    @PostMapping(value = "/validate",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> validateAppToken(ValidateTokenDto dto){
        return tokenService.validateToken(dto);
    }
}