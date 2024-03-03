package org.example.bankservice.repository;

import org.example.bankservice.domain.Account;
import org.example.bankservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUser(User user);

}
