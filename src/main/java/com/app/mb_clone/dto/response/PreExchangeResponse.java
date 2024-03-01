package com.app.mb_clone.dto.response;

import com.app.mb_clone.model.Account;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreExchangeResponse {
    private Account senderAcc;
    private Account receiverAcc;
    private String message;
    private int amount;
}
