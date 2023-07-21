package com.iwor.spring.database.repository;

import com.iwor.spring.database.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final ConnectionPool pool2;
}
