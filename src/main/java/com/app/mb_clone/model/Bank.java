package com.app.mb_clone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "banks")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String logo;

    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("bank")
    private Set<Account> accounts;

    private String code;
}
