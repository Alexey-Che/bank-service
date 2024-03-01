package org.example.bankservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Table(name = "accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "balance")
    double balance;

    @Column(name = "initial_balance")
    double initialBalance;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    public Account(double balance, User user) {
        this.user = user;
        this.balance = balance;
        this.initialBalance = balance;
    }

    public Account() {
    }
}
