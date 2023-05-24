package com.swagou.test.controller;

import com.swagou.test.dto.LoginRequest;
import com.swagou.test.dto.LoginResponse;
import com.swagou.test.dto.SignupRequest;
import com.swagou.test.entity.CryptoUser;
import com.swagou.test.service.CryptoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class CryptoUserController {

    @Autowired
    private CryptoUserService cryptoUserService;

    private final Logger LOGGER = Logger.getLogger(CryptoUserController.class.getName());

    @GetMapping(value = "/users")
    public ResponseEntity<List<CryptoUser>> getUsers() {
        List<CryptoUser> cryptoUsers = new ArrayList<>();
        try {
            cryptoUsers = cryptoUserService.getUsers();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cryptoUsers);
        } catch (Exception e) {
            LOGGER.info("Error getUsers api: {}" + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(cryptoUsers);
        }
    }

    @GetMapping(value = "/user/{cryptoUserId}")
    public ResponseEntity<CryptoUser> getUserById(@PathVariable("cryptoUserId")  Long cryptoUserId) {
        CryptoUser cryptoUser = new CryptoUser();
        try {
            cryptoUser = cryptoUserService.getUserById(cryptoUserId);

            if (cryptoUser == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(cryptoUser);
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cryptoUser);
        } catch (Exception e) {
            LOGGER.info("Error getUserById api: {}" + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(cryptoUser);
        }
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Void> insertUser(@RequestBody CryptoUser cryptoUser) {
        try {
            cryptoUserService.insertUser(cryptoUser);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build();
        } catch (Exception e) {
            LOGGER.info("Error insertUser api: {}" + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PutMapping(value = "/user")
    public ResponseEntity<Void> updateUser(@RequestBody CryptoUser cryptoUser) {
        try {
            cryptoUserService.updateUser(cryptoUser);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build();
        } catch (Exception e) {
            LOGGER.info("Error updateUser api: {}" + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @DeleteMapping(value = "/user/{cryptoUserId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("cryptoUserId") Long cryptoUserId) {
        try {
            cryptoUserService.deleteUserById(cryptoUserId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            LOGGER.info("Error deleteUserById api: {}" + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = "/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequest signupRequest) {
        try {
            cryptoUserService.signup(signupRequest.getCryptoUser(), signupRequest.getWalletType());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build();
        } catch (Exception e) {
            LOGGER.info("Error signup api: {}" + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping(value = "/info/{symbol}")
    public ResponseEntity<String> info(@PathVariable String symbol) {
        try {
            String response = cryptoUserService.info(symbol);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (Exception e) {
            LOGGER.info("Error info api: {}" + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            String jwt = cryptoUserService.login(loginRequest.getEmail(), loginRequest.getPassword());

            if (jwt == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .build();
            }

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken(jwt);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(loginResponse);
        } catch (Exception e) {
            LOGGER.info("Error login api: {}" + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
