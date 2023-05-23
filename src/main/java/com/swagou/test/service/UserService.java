package com.swagou.test.service;

import com.swagou.test.entity.User;
import com.swagou.test.entity.UserWallet;
import com.swagou.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public void insertUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    //////////////////////////////////////////////////////////////////////

    public void signup(User user, String walletType) throws Exception {

        String address = "";
        switch (walletType) {
            case "BTC":
                break;
            case "ETH":
                break;
            default:
                throw new Exception("Not supported");
        }
        UserWallet userWallet = new UserWallet();
        userWallet.setAddress(address);
        user.setUserWallet(userWallet);

        userRepository.save(user);
    }

    public void info(Long userId) {
        userRepository.deleteById(userId);
    }
}