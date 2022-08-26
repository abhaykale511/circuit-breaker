package com.demo.dao.impl;

import com.demo.dao.UserRepo;
import com.demo.dto.UserDetails;
import org.springframework.stereotype.Repository;

import java.net.SocketTimeoutException;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo {

	public static int count = 0;

	@Override
	public List<UserDetails> getAllUsers() throws SocketTimeoutException {
		count++;
		if (count < 5) {
			return List.of(new UserDetails("Machindra", "Kale", 12345), new UserDetails("Mitansh", "Kale", 23456),
					new UserDetails("Mayuri", "Kale", 34567));
		} else if (count > 10) {
			return List.of(new UserDetails("Machindra", "Kale", 12345), new UserDetails("Mitansh", "Kale", 23456),
					new UserDetails("Mayuri", "Kale", 34567));
		} else {

			throw new SocketTimeoutException("socket time out");
		}
	}
}
