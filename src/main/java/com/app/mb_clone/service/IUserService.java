package com.app.mb_clone.service;

import com.app.mb_clone.dto.request.LoginFormRequest;
import com.app.mb_clone.dto.request.RegisterForm;
import com.app.mb_clone.dto.response.JwtResponse;
import com.app.mb_clone.dto.response.RegisterResponse;
import com.app.mb_clone.model.User;
import com.app.mb_clone.service.design.IGenericService;

import java.util.Optional;

public interface IUserService extends IGenericService<User> {
    JwtResponse login(LoginFormRequest loginFormRequest);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<User> findByUsernameOrEmailOrPhoneNumber(String findingUserData);
    RegisterResponse register(RegisterForm registerForm);

}
