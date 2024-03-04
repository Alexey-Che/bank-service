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
@Table(name = "phone_numbers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "phone", nullable = false, unique = true)
    String phone;

    @Column(name = "user_id", nullable = false)
    Long userId;

    public PhoneNumber(String phone) {
        this.phone = phone;
    }
}
