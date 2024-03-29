package org.example.bankservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "balance", nullable = false)
    double balance;

    @Column(name = "initial_balance", nullable = false)
    double initialBalance;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    User user;

    public Account(double balance) {
        this.balance = balance;
        this.initialBalance = balance;
    }
}
