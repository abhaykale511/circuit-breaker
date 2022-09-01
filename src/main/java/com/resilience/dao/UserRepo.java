package com.resilience.dao;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;

import com.resilience.dto.UserDetails;

public interface UserRepo {

    Optional<List<UserDetails>> getAllUsers() throws SocketTimeoutException;
}
