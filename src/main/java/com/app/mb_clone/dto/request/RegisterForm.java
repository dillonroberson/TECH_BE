package com.app.mb_clone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterForm {

    private String name;

    private String password;

    private String phone;

    private String email;

    private Set<String> roles;
}
