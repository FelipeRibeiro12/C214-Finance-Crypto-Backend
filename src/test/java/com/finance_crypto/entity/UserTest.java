package com.finance_crypto.entity;

import com.finance_crypto.controller.dto.LoginRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void deveRetornarTrueQuandoSenhaEstiverCorreta() {
        User user = new User();
        user.setPassword(passwordEncoder.encode("123456"));

        LoginRequestDTO loginRequest = new LoginRequestDTO("igor", "123456");

        assertTrue(user.isLoginCorrect(loginRequest, passwordEncoder));
    }

    @Test
    void deveRetornarFalseQuandoSenhaEstiverIncorreta() {
        User user = new User();
        user.setPassword(passwordEncoder.encode("123456"));

        LoginRequestDTO loginRequest = new LoginRequestDTO("igor", "654321");

        assertFalse(user.isLoginCorrect(loginRequest, passwordEncoder));
    }
}