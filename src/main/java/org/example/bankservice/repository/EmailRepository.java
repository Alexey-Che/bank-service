package org.example.bankservice.repository;

import org.example.bankservice.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query("select e.email from Email e where e.userId = :userId")
    List<String> findAllByUserId(@Param("userId") Long userId);
}
