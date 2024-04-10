package org.example.bankservice.repository;

import io.micrometer.core.annotation.Timed;
import org.example.bankservice.domain.Email;
import org.example.bankservice.domain.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {

    @Timed("findPhonesByUserId")
    @Query("select p from PhoneNumber p where p.userId = :userId")
    List<PhoneNumber> findAllByUserId(@Param("userId") Long userId);

    @Timed("findPhone")
    @Query("select p from PhoneNumber p where p.userId = :userId and p.phone = :phone")
    Optional<PhoneNumber> findByPhoneAndUserId(@Param("phone") String email, @Param("userId") Long userId);
}
