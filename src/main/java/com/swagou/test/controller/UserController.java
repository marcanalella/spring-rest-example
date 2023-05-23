package com.swagou.test.controller;

import com.swagou.test.entity.User;
import com.swagou.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        //LOGGER.info("Request JSON {} : " + jsonString.toString());
        //LOGGER.info("Request Values {} : " + values);
        List<User> users = new ArrayList<>();
        try {
            users = userService.getUsers();
            //LOGGER.info("Response {} : " + response);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(users);
        } catch (Exception e) {
            //LOGGER.info("Error Parsing JSON: {}", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(users);
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity<User> getUserById(@RequestParam Long userId) {
        //LOGGER.info("Request JSON {} : " + jsonString.toString());
        //LOGGER.info("Request Values {} : " + values);
        User user = new User();
        try {
            user = userService.getUserById(userId);
            //LOGGER.info("Response {} : " + response);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user);
        } catch (Exception e) {
            //LOGGER.info("Error Parsing JSON: {}", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(user);
        }
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Void> insertUser(@RequestParam User user) {
        //LOGGER.info("Request JSON {} : " + jsonString.toString());
        //LOGGER.info("Request Values {} : " + values);
        try {
            userService.insertUser(user);
            //LOGGER.info("Response {} : " + response);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build();
        } catch (Exception e) {
            //LOGGER.info("Error Parsing JSON: {}", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PutMapping(value = "/user")
    public ResponseEntity<Void> updateUser(@RequestParam User user) {
        //LOGGER.info("Request JSON {} : " + jsonString.toString());
        //LOGGER.info("Request Values {} : " + values);
        try {
            userService.updateUser(user);
            //LOGGER.info("Response {} : " + response);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build();
        } catch (Exception e) {
            //LOGGER.info("Error Parsing JSON: {}", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<Void> deleteUserById(@RequestParam Long userId) {
        //LOGGER.info("Request JSON {} : " + jsonString.toString());
        //LOGGER.info("Request Values {} : " + values);
        try {
            userService.deleteUserById(userId);
            //LOGGER.info("Response {} : " + response);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            //LOGGER.info("Error Parsing JSON: {}", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    //////////////////////////////////////////////////////////////////////

    @PostMapping(value = "/signup")
    public ResponseEntity<Void> signup(@RequestParam User user, String walletType) {
        //LOGGER.info("Request JSON {} : " + jsonString.toString());
        //LOGGER.info("Request Values {} : " + values);
        try {
            userService.signup(user, walletType);
            //LOGGER.info("Response {} : " + response);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            //LOGGER.info("Error Parsing JSON: {}", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping(value = "/info")
    public ResponseEntity<Void> info(@RequestParam String symbol) {
        //LOGGER.info("Request JSON {} : " + jsonString.toString());
        //LOGGER.info("Request Values {} : " + values);
        try {
            userService.info(symbol);
            //LOGGER.info("Response {} : " + response);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            //LOGGER.info("Error Parsing JSON: {}", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
