package com.tania.service;

import java.util.List;

import com.tania.exception.UserException;
import com.tania.model.User;

public interface UserService {

	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findUserByEmail(String email) throws UserException;


}
