package com.app.mb_clone.controller;

import com.app.mb_clone.dto.request.LoginFormRequest;
import com.app.mb_clone.dto.request.RegisterForm;
import com.app.mb_clone.dto.response.JwtResponse;
import com.app.mb_clone.dto.response.RegisterResponse;
import com.app.mb_clone.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@Valid @RequestBody LoginFormRequest loginForm) {
        JwtResponse jwtResponse = userService.login(loginForm);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@Valid @RequestBody RegisterForm registerForm) {
        RegisterResponse response = userService.register(registerForm);
        return ResponseEntity.ok().body(response);
    }
}
