package com.demo.dao;

import com.demo.dto.UserDetails;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;

public interface UserRepo {

    Optional<List<UserDetails>> getAllUsers() throws SocketTimeoutException;
}
