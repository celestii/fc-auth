package com.example.fcauth.controller;

import com.example.fcauth.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginController {
    private final LoginService loginService;

    @GetMapping("/kakao/callback")
    public ResponseEntity callback(@RequestParam("code") String code) {
        return loginService.login(code);
    }
}
