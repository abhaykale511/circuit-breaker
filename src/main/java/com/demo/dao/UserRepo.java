package com.demo.dao;

import com.demo.dto.UserDetails;

import java.net.SocketTimeoutException;
import java.util.List;

public interface UserRepo {

    List<UserDetails> getAllUsers() throws SocketTimeoutException;
}
