package org.example.bankservice.repository;

import org.example.bankservice.domain.Email;
import org.example.bankservice.domain.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {

    @Query("select p.phone from PhoneNumber p where p.userId = :userId")
    List<String> findAllByUserId(@Param("userId") Long userId);

    Optional<PhoneNumber> findByPhoneAndUserId(@Param("phone") String email, @Param("userId") Long userId);
}
