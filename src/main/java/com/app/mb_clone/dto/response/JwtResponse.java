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
public class JwtResponse {
    private String status;
    private long expiredTime;
    private String type;
    private String token;
    private User user;
    private Collection<? extends GrantedAuthority> roles;
}
