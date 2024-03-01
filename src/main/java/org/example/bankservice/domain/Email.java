package org.example.bankservice.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "emails")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email")
    String email;

    @Column(name = "user_id")
    Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(id, email1.id)
                && Objects.equals(email, email1.email)
                && Objects.equals(userId, email1.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, userId);
    }
}
