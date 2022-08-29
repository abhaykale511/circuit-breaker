package com.demo.dao;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;

import com.demo.dto.UserDetails;

public interface UserRepo {

    Optional<List<UserDetails>> getAllUsers() throws SocketTimeoutException;
}
