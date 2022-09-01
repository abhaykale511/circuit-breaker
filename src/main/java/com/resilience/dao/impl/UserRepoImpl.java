package com.resilience.dao.impl;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.resilience.dao.UserRepo;
import com.resilience.dto.UserDetails;

@Repository
public class UserRepoImpl implements UserRepo {

	private static final Logger LOG = LoggerFactory.getLogger(UserRepoImpl.class); 
	
	public static int count = 0;

	@Override
	public Optional<List<UserDetails>> getAllUsers() throws SocketTimeoutException {
		LOG.debug("Inside UserDAO and call count:{}",count);
	
		count++;
		if (count < 5) {
			return Optional.of(List.of(new UserDetails("Machindra", "Kale", 12345), new UserDetails("Mitansh", "Kale", 23456),
					new UserDetails("Mayuri", "Kale", 34567)));
		} else if (count > 10) {
			return Optional.of(List.of(new UserDetails("Machindra", "Kale", 12345), new UserDetails("Mitansh", "Kale", 23456),
					new UserDetails("Mayuri", "Kale", 34567)));
		} else {

			throw new SocketTimeoutException("socket time out");
		}
	}
}
