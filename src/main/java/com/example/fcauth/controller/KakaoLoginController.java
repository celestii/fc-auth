package com.example.fcauth.controller;

import com.example.fcauth.model.KakaoUserInfoRespDto;
import com.example.fcauth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginController {
    private final KakaoService kakaoService;

    @GetMapping("/kakao/callback")
    public ResponseEntity callback(@RequestParam("code") String code) {
        String token = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoRespDto userInfo = kakaoService.getUserFromKakao(token);
        log.info("userInfo " + userInfo.getKakaoAccount().getProfile().getNickName());
        return new ResponseEntity(HttpStatus.OK);
    }
}
