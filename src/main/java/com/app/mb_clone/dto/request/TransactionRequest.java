package com.app.mb_clone.dto.request;

import com.app.mb_clone.model.Account;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private String pin;
    private Long senderAccId;
    private Long receiverAccId;
    private String message;
    private int amount;
}
