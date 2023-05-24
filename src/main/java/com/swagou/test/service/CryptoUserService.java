package com.swagou.test.service;

import com.swagou.test.controller.CryptoUserController;
import com.swagou.test.entity.CryptoUser;
import com.swagou.test.entity.CryptoUserWallet;
import com.swagou.test.repository.CryptoUserRepository;
import com.swagou.test.util.Utils;
import jakarta.transaction.Transactional;
import nl.flotsam.xeger.Xeger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CryptoUserService {

    @Autowired
    private CryptoUserRepository cryptoUserRepository;

    private final Logger LOGGER = Logger.getLogger(CryptoUserController.class.getName());

    @Value("${regex.btc.regexp}")
    private String BTC_REGEX;

    @Value("${regex.eth.regexp}")
    private String ETH_REGEX;

    @Value("${api.url}")
    private String URL;

    @Value("${jwt.secrekey}")
    private String SECRET_KEY;
    private final RestTemplate client = new RestTemplate();

    public List<CryptoUser> getUsers() {
        return (List<CryptoUser>) cryptoUserRepository.findAll();
    }

    public CryptoUser getUserById(Long cryptoUserId) {
        Optional<CryptoUser> cryptoUser = cryptoUserRepository.findById(cryptoUserId);
        return cryptoUser.orElse(null);
    }

    public void insertUser(CryptoUser cryptoUser) {
        cryptoUserRepository.save(cryptoUser);
    }

    public void updateUser(CryptoUser cryptoUser) {
        cryptoUserRepository.save(cryptoUser);
    }

    public void deleteUserById(Long cryptoUserId) {
        cryptoUserRepository.deleteById(cryptoUserId);
    }

    //////////////////////////////////////////////////////////////////////

    @Transactional
    public void signup(CryptoUser cryptoUser, String walletType) throws Exception {
        if(cryptoUserRepository.findByEmail(cryptoUser.getEmail()).isPresent()) {
            throw new Exception("Crypto User already exists");
        }

        String address;
        Xeger generator;
        switch (walletType.toLowerCase()) {
            case "btc" -> {
                generator = new Xeger(BTC_REGEX);
                address = generator.generate();
            }
            case "eth" -> {
                generator = new Xeger(ETH_REGEX);
                address = generator.generate();
            }
            default -> {
                LOGGER.info("Error signup service - Wallet not supported - Received: {}" + walletType);
                throw new Exception("Not supported");
            }
        }

        CryptoUserWallet cryptoUserWallet = new CryptoUserWallet();
        cryptoUserWallet.setAddress(address);
        cryptoUser.setCryptoUserWallet(cryptoUserWallet);
        cryptoUser.setPassword(BCrypt.hashpw(cryptoUser.getPassword(), BCrypt.gensalt()));
        cryptoUserRepository.save(cryptoUser);
    }

    public String info(String pair) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", "d25fb401ccmshb944d22188b678dp143726jsn0de8779d2730");
        headers.add("X-RapidAPI-Host",  "alpha-vantage.p.rapidapi.com");
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromUri(URI.create(URL))
                .queryParam("from_currency", pair)
                .queryParam("function", "CURRENCY_EXCHANGE_RATE")
                .queryParam("to_currency", "EUR")
                .build()
                .toUri();

        return client.exchange(uri, HttpMethod.GET, requestEntity, String.class).getBody();
    }

    public String login(String email, String password) {
        Optional<CryptoUser> cryptoUser = cryptoUserRepository.findByEmail(email);
        if (cryptoUser.isPresent()) {
            if (BCrypt.checkpw(password, cryptoUser.get().getPassword())) {
                return Utils.createJWT(cryptoUser.get().getId().toString(), cryptoUser.get().getFirstName(), 720000, SECRET_KEY); //20 minuti
            }
        } else {
            LOGGER.info("User not exists");
            return null;
        }
        return null;
    }
}