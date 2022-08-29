package com.demo.services.impl;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.UserRepo;
import com.demo.dto.UserDetails;
import com.demo.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private static int count = 0;

	@Autowired
	private UserRepo userRepo;

	@Override
	public List<UserDetails> getUsers() {
		LOG.debug("Inside UserService and call count:{}", count);
		count++;
		try {
			return userRepo.getAllUsers().orElseGet(ArrayList::new);
		} catch (SocketTimeoutException e) {
			LOG.error("DAO call failed", e);
		}
		return new ArrayList<>();
	}
}
