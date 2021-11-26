package com.learn.newlearn1.account;

import com.learn.newlearn1.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
