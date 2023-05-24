package com.swagou.test.dto;

import com.swagou.test.entity.CryptoUser;

public class SignupRequest {
    private CryptoUser cryptoUser;

    private String walletType;

    public CryptoUser getCryptoUser() {
        return cryptoUser;
    }

    public void setCryptoUser(CryptoUser cryptoUser) {
        this.cryptoUser = cryptoUser;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }
}
