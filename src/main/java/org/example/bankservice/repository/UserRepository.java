package org.example.bankservice.repository;

import org.example.bankservice.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u join u.emails e where e.email = :email")
    List<User> findByEmail(@Param("email") String email, Pageable page);

    @Query("select u from User u where u.birthDate = :date")
    List<User> findByBirthDate(@Param("date") Date date, Pageable page);

    @Query("select u from User u join u.phoneNumbers p where p.phone = :phone")
    List<User> findByPhoneNumber(@Param("phone") String phone, Pageable page);

    List<User> findByFullNameStartsWith(@Param("fullName") String fullName, Pageable page);

    Optional<User> findByUsername(@Param("username") String username);

    boolean existsByUsername(String username);

    @Query("select case when count(u) > 0 then true else false end from User u join u.emails e where e.email in (:emails)")
    boolean existsByEmail(@Param("emails") List<String> emails);

    @Query("select case when count(u) > 0 then true else false end from User u join u.phoneNumbers e where e.phone in (:phones)")
    boolean existsByPhoneNumbers(@Param("phones") List<String> phones);
}
