package com.example.fcauth.util;

import com.example.fcauth.model.Employee;
import com.example.fcauth.model.Role;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtUtilTest {

    @Test
    public void testCreateToken() {
        String testNick = "john doe";
        Employee employee = Employee.builder()
                .kakaoNickName(testNick)
                .roles(new HashSet<>() {{
                    add(Role.builder()
                            .name("ROLE_USER")
                            .build());
                }})
                .build();

        String token = JwtUtil.createToken(employee);

        assertEquals(testNick, JwtUtil.parseToken(token).get("nickname"));
    }

    @Test
    public void test_role() {
        String testNick = "john doe";
        Role role1 = Role.builder()
                .id(1L)
                .name("role1")
                .build();

        Role role2 = Role.builder()
                .id(2L)
                .name("role2")
                .build();

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role1);
        roleSet.add(role2);


        Employee employee = Employee.builder()
                .kakaoNickName(testNick)
                .roles(roleSet)
                .build();

        String token = JwtUtil.createToken(employee);
        List res = JwtUtil.parseToken(token).get("roles", List.class);
        assertEquals(roleSet.size(), res.size());
        assertTrue(res.contains(role1.getName()));
        assertTrue(res.contains(role2.getName()));
    }
}
