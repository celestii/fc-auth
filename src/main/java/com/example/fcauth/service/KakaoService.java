package com.example.fcauth.service;

import com.example.fcauth.dto.AppTokenRespDto;
import com.example.fcauth.dto.ValidateTokenDto;
import com.example.fcauth.model.Api;
import com.example.fcauth.model.App;
import com.example.fcauth.model.KakaoTokenRespDto;
import com.example.fcauth.model.KakaoUserInfoRespDto;
import com.example.fcauth.repository.ApiRepository;
import com.example.fcauth.repository.AppRepository;
import com.example.fcauth.util.JwtUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final AppRepository appRepository;
    private final ApiRepository apiRepository;

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    private final String KAKAO_AUTH_URL = "https://kauth.kakao.com";
    private final String KAKAO_USER_URL = "https://kapi.kakao.com";

    public KakaoUserInfoRespDto getUserFromKakao(String accessToken){
        return WebClient.create(KAKAO_USER_URL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .bodyToMono(KakaoUserInfoRespDto.class)
                .block();
    }

    public String getAccessTokenFromKakao(String code) {
        KakaoTokenRespDto kakaoTokenRespDto =
                WebClient.create(KAKAO_AUTH_URL)
                        .post()
                        .uri(uriBuilder -> uriBuilder
                                .scheme("https")
                                .path("/oauth/token")
                                .queryParam("grant_type", "authorization_code")
                                .queryParam("client_id", clientId)
                                .queryParam("redirect_uri", redirectUri)
                                .queryParam("code", code)
                                .build())
                        .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                        .retrieve()
                        .bodyToMono(KakaoTokenRespDto.class)
                        .block();

        return kakaoTokenRespDto.getAccessToken();
    }

    public AppTokenRespDto createAppToken(Long appId){
        App app = appRepository.getById(appId);
        String token = JwtUtil.createAppToken(app);
        return AppTokenRespDto.builder()
                .token(token)
                .build();
    }

    public ResponseEntity<String> validateToken(ValidateTokenDto dto) {
        Api api = apiRepository.findByMethodAndPath(dto.getMethod(), dto.getPath());
        ResponseEntity resp = JwtUtil.validateAppToken(dto, api);
        if(resp.getStatusCode().is2xxSuccessful()){
            Long appId = Long.valueOf(JwtUtil.parseSubject(dto.getToken()));
//            if(!customRateLimiter.tryConsume(appId, api.getId())){
//                log.error("TOO MANY REQUESTS");
//                return new ResponseEntity<>("too many requests", HttpStatus.TOO_MANY_REQUESTS);
//            }
        }
        return resp;
    }
}
