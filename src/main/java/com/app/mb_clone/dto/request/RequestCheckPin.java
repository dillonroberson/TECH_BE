package com.app.mb_clone.dto.request;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCheckPin {
    private String message;
    private String status;
}
