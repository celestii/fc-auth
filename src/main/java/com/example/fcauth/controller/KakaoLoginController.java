package com.example.fcauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KakaoLoginController {
    @GetMapping("/kakao/callback")
    public ResponseEntity callback(@RequestParam("code") String code) {
        log.info("code " + code);
        return new ResponseEntity(HttpStatus.OK);
    }
}
