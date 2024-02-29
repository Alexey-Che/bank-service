package org.example.bankservice.repository;

import org.example.bankservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u join u.emails e where e.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("select u.id from User u join u.emails e where e.email = :email")
    Optional<Long> findUserIdByEmail(@Param("email") String email);
}
