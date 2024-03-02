package org.example.bankservice.repository;

import org.example.bankservice.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query("select e.email from Email e where e.userId = :userId")
    List<String> findAllByUserId(@Param("userId") Long userId);

    Optional<Email> findByEmailAndUserId(@Param("email") String email, @Param("userId") Long userId);
}
