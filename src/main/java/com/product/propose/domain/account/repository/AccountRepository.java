package com.product.propose.domain.account.repository;

import com.product.propose.domain.account.entity.Account;
import com.product.propose.domain.account.repository.extension.AccountRepositoryExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryExtension {

    Account findByEmail(String email);
}