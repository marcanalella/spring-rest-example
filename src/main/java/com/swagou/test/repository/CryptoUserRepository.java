package com.swagou.test.repository;

import com.swagou.test.entity.CryptoUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CryptoUserRepository extends CrudRepository<CryptoUser, Long> {

    Optional<CryptoUser> findByEmail(String email);

}
