package com.app.mb_clone.dto.request;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginFormRequest {
    private String username;
    private String password;
}
