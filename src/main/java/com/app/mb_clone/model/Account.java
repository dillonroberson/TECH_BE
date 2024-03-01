package com.app.mb_clone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String number;

    private String PIN;

    @NotNull
    private int balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("accounts")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    @JsonIgnoreProperties("accounts")
    private Bank bank;
}
