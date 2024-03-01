package org.example.bankservice.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(fullName, user.fullName)
                && Objects.equals(birthDate, user.birthDate)
                && Objects.equals(password, user.password)
                && Objects.equals(phoneNumbers, user.phoneNumbers)
                && Objects.equals(emails, user.emails)
                && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, birthDate, password, phoneNumbers, emails, role);
    }
}


