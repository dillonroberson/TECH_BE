package com.app.mb_clone.dto.response;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private String status;
    private String message;
    private Object data;
}
