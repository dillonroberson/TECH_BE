package com.app.mb_clone.dto.response;

import com.app.mb_clone.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private String status;
    private User user;
    private String message;
}
