package com.swagou.test.repository;

import com.swagou.test.entity.CryptoUser;
import com.swagou.test.entity.CryptoUserWallet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CryptoUserWalletRepository extends CrudRepository<CryptoUserWallet, Long> {


}
