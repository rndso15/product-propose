package com.product.propose.domain.account.repository.extension.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.product.propose.domain.account.repository.extension.AccountRepositoryExtension;

public class AccountRepositoryExtensionImpl implements AccountRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public AccountRepositoryExtensionImpl(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }


    // ===== ===== ===== ===== ===== override method ===== ===== ===== ===== =====

    // ===== ===== ===== ===== ===== boolean expression ===== ===== ===== ===== =====
}
