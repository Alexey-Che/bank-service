package org.example.bankservice.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "full_name")
    String fullName;

    @Temporal(TemporalType.DATE)
    Date birthDate;

    @Column(name = "password")
    String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Account account;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<PhoneNumber> phoneNumbers;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<Email> emails;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    UserRole role;

}


