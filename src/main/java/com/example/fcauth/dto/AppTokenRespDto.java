package com.example.fcauth.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppTokenRespDto {
    private String token;
}