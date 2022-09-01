package com.resilience.services;

import java.util.List;

import com.resilience.dto.UserDetails;

public interface UserService {

    List<UserDetails> getUsers();
}
