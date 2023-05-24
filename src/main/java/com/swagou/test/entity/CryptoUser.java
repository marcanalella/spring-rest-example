package com.swagou.test.entity;

import jakarta.persistence.*;

@Entity
public class CryptoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crypto_user_wallet_id", referencedColumnName = "id")
    private CryptoUserWallet cryptoUserWallet;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CryptoUserWallet getCryptoUserWallet() {
        return cryptoUserWallet;
    }

    public void setCryptoUserWallet(CryptoUserWallet cryptoUserWallet) {
        this.cryptoUserWallet = cryptoUserWallet;
    }
}
