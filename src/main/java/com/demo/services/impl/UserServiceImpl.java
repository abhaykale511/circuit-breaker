package com.demo.services.impl;

import com.demo.dao.UserRepo;
import com.demo.dto.UserDetails;
import com.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<UserDetails> getUsers() {
        try {
            return userRepo.getAllUsers();
        } catch (SocketTimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
