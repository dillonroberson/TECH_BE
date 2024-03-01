package com.app.mb_clone.dto.request;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreExchangeRequest {
    private String receiverNumber;
    private String message;
    private int amount;
    private Long bankId;
    private Long senderId;
    private String receiverName;
}
