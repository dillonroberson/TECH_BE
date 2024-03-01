package com.app.mb_clone.dto.request;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceUpdateRequest {
    private String message;
    private Long timestamp;
    private String amount;
    private String remain;
    private String balance;
    private String number;
}
