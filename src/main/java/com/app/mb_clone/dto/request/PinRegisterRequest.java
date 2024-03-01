package com.app.mb_clone.dto.request;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinRegisterRequest {
    private String pin;
}
