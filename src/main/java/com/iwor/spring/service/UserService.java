package com.iwor.spring.service;

import com.iwor.spring.database.entity.Company;
import com.iwor.spring.database.repository.CrudRepository;
import com.iwor.spring.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;
}
