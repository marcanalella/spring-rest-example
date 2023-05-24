package com.swagou.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.swagou.test.entity.CryptoUser;
import com.swagou.test.repository.CryptoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CryptoUserServiceTest {

    @Mock
    private CryptoUserRepository cryptoUserRepository;

    @InjectMocks
    private CryptoUserService cryptoUserService;

    private CryptoUser cryptoUser;

    @BeforeEach
    public void setup() {
        cryptoUser = new CryptoUser();
        cryptoUser.setFirstName("Vitalik");
        cryptoUser.setLastName("Buterin");
        cryptoUser.setEmail("vitalik@buretin.com");
        cryptoUser.setPassword("122345");
    }


    @Test
    public void givenCryptoUsersList_whenGetAllCryptoUsers_thenReturnCryptoUsersList() {
        // given - precondition or setup

        CryptoUser cryptoUser1 = new CryptoUser();
        cryptoUser1.setFirstName("Satoshi");
        cryptoUser1.setLastName("Nakamoto");
        cryptoUser1.setEmail("sat@nak.com");
        cryptoUser1.setPassword("122345");

        given(cryptoUserRepository.findAll()).willReturn(List.of(cryptoUser, cryptoUser1));

        // when -  action or the behaviour that we are going test
        List<CryptoUser> CryptoUserList = cryptoUserService.getUsers();

        // then - verify the output
        assertThat(CryptoUserList).isNotNull();
        assertThat(CryptoUserList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmptyCryptoUsersList_whenGetAllCryptoUsers_thenReturnEmptyCryptoUsersList() {
        // given - precondition or setup

        CryptoUser cryptoUser1 = new CryptoUser();
        cryptoUser1.setFirstName("Satoshi");
        cryptoUser1.setLastName("Nakamoto");
        cryptoUser1.setEmail("sat@nak.com");
        cryptoUser1.setPassword("122345");

        given(cryptoUserRepository.findAll()).willReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        List<CryptoUser> CryptoUserList = cryptoUserService.getUsers();

        // then - verify the output
        assertThat(CryptoUserList).isEmpty();
        assertThat(CryptoUserList.size()).isEqualTo(0);
    }


    @Test
    public void givenCryptoUserId_whenDeleteCryptoUser_thenNothing() {
        // given - precondition or setup
        long cryptoUserId = 1L;

        willDoNothing().given(cryptoUserRepository).deleteById(cryptoUserId);

        // when -  action or the behaviour that we are going test
        cryptoUserService.deleteUserById(cryptoUserId);

        // then - verify the output
        verify(cryptoUserRepository, times(1)).deleteById(cryptoUserId);
    }
}