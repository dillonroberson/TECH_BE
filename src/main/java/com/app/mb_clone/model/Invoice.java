package com.app.mb_clone.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invoices")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private Account receiverAcc;

    @ManyToOne
    @JoinColumn(name = "sender")
    private Account senderAcc;

    private int amount;
    private String message;

    private Long timeStamp;
    private String transactionCode;
    private int remainSendAcc;
    private int remainReceiveAcc;
}
