package com.swagou.test.repository;

import com.swagou.test.entity.CryptoUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CryptoUserRepositoryTest {
    @Autowired
    private CryptoUserRepository cryptoUserRepository;

    private CryptoUser cryptoUser;

    @BeforeEach
    public void setup(){
        cryptoUser = new CryptoUser();
        cryptoUser.setFirstName("Vitalik");
        cryptoUser.setLastName("Buterin");
        cryptoUser.setEmail("vitalik@buretin.com");
        cryptoUser.setPassword("122345");
    }

    @Test
    public void givenCryptoUserObject_whenSave_thenReturnSavedCryptoUser(){
        
        // when - action or the behaviour that we are going test
        CryptoUser savedCryptoUser = cryptoUserRepository.save(cryptoUser);

        // then - verify the output
        assertThat(savedCryptoUser).isNotNull();
        assertThat(savedCryptoUser.getId()).isGreaterThan(0);
    }

    @Test
    public void givenCryptoUsersList_whenFindAll_thenCryptoUsersList(){
        // given - precondition or setup
        CryptoUser cryptoUser1 = new CryptoUser();
        cryptoUser1.setFirstName("Satoshi");
        cryptoUser1.setLastName("Nakamoto");
        cryptoUser1.setEmail("sat@nak.it");
        cryptoUser1.setPassword("122345");

        CryptoUser savedCryptoUser = cryptoUserRepository.save(cryptoUser);
        CryptoUser savedCryptoUser1 = cryptoUserRepository.save(cryptoUser1);

        // when -  action or the behaviour that we are going test
        List<CryptoUser> cryptoUsers = (List<CryptoUser>) cryptoUserRepository.findAll();

        // then - verify the output
        assertThat(cryptoUsers).isNotNull();
        assertThat(cryptoUsers.size()).isEqualTo(2);

    }

    @Test
    public void givenCryptoUserObject_whenFindById_thenReturnCryptoUserObject(){

        cryptoUserRepository.save(cryptoUser);

        // when -  action or the behaviour that we are going test
        CryptoUser cryptoUserDB = cryptoUserRepository.findById(cryptoUser.getId()).get();

        // then - verify the output
        assertThat(cryptoUserDB).isNotNull();
    }

    @Test
    public void givenCryptoUserObject_whenUpdateCryptoUser_thenReturnUpdatedCryptoUser(){

        cryptoUserRepository.save(cryptoUser);

        // when -  action or the behaviour that we are going test
        CryptoUser savedCryptoUser = cryptoUserRepository.findById(cryptoUser.getId()).get();
        savedCryptoUser.setEmail("gen@asd.com");
        savedCryptoUser.setFirstName("Pino");
        CryptoUser cryptoUser1 =  cryptoUserRepository.save(savedCryptoUser);

        // then - verify the output
        assertThat(cryptoUser1.getEmail()).isEqualTo("gen@asd.com");
        assertThat(cryptoUser1.getFirstName()).isEqualTo("Pino");
    }

    @Test
    public void givenCryptoUserObject_whenDelete_thenRemoveCryptoUser(){

        cryptoUserRepository.save(cryptoUser);

        // when -  action or the behaviour that we are going test
        cryptoUserRepository.deleteById(cryptoUser.getId());
        Optional<CryptoUser> cryptoUserOptional = cryptoUserRepository.findById(cryptoUser.getId());

        // then - verify the output
        assertThat(cryptoUserOptional).isEmpty();
    }
}
