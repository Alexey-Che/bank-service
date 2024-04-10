package org.example.bankservice.repository;

import io.micrometer.core.annotation.Timed;
import org.example.bankservice.domain.Account;
import org.example.bankservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Timed("findUser")
    Optional<Account> findByUser(User user);

    @Timed(value = "findAllAccounts")
    List<Account> findAll();

}
