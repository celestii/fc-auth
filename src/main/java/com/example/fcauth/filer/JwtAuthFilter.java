package com.example.fcauth.filer;

import com.example.fcauth.model.Employee;
import com.example.fcauth.repository.EmployeeRepository;
import com.example.fcauth.service.KakaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final KakaoService kakaoService;
    private final EmployeeRepository employeeRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7);
            String nickName = kakaoService.getUserFromKakao(accessToken).getKakaoAccount().getProfile().getNickName();
            if(employeeRepository.existsByKakaoNickName(nickName)) {
                Employee employee = employeeRepository.findByKakaoNickName(nickName);
                Authentication authentication = new TestingAuthenticationToken(employee.getFirstName(), "password", "ROLE_TEST");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
