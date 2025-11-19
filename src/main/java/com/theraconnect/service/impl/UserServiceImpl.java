package com.theraconnect.service.impl;
 
import java.util.List;

import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
 
import com.theraconnect.enums.Role;

import com.theraconnect.model.User;

import com.theraconnect.repository.UserRepository;

import com.theraconnect.service.UserService;
 
@Service

public class UserServiceImpl implements UserService{
 
 
	 @Autowired

	    private UserRepository userRepository;
 
	    @Override

	    public User createUser(User user) {

	        if (user == null) {

	            throw new IllegalArgumentException("User cannot be null.");

	        }

	        if (user.getEmail() == null || user.getEmail().isEmpty()) {

	            throw new IllegalArgumentException("Email must not be empty.");

	        }

	        return userRepository.save(user);

	    }
 
	    @Override

	    public Optional<User> getUserById(Long id) {

	        if (id == null || id <= 0) {

	            throw new IllegalArgumentException("Invalid ID provided.");

	        }

	        return userRepository.findById(id);

	    }
 
	    @Override

	    public List<User> getAllUsers() {

	        return userRepository.findAll();

	    }
 
	    @Override

	    public void deleteUser(Long id) {

	        if (id == null || id <= 0) {

	            throw new IllegalArgumentException("Invalid ID provided.");

	        }

	        if (!userRepository.existsById(id)) {

	            throw new IllegalArgumentException("User with ID " + id + " does not exist.");

	        }

	        Optional<User> userOpt = userRepository.findById(id);

	        User user = userOpt.get();

	        if (user.getRole() == Role.ADMIN ) {

	            throw new IllegalStateException("Cannot delete system admin.");

	        }

	        userRepository.deleteById(id);

	    }
 
	    @Override

	    public Optional<User> getUserByEmail(String email) {

	        if (email == null || email.isEmpty()) {

	            throw new IllegalArgumentException("Email must not be empty.");

	        }

	        if (!email.contains("@")) {

	            throw new IllegalArgumentException("Email format is invalid.");

	        }

	        return userRepository.findByEmail(email);

	    }

	    @Override

		 public User save(User user) {

		     return userRepository.save(user);

		 }
 
	    @Override

	    public List<User> getUsersByRole(String role) {

	        try {

	            Role enumRole = Role.valueOf(role.trim().toUpperCase());

	            return userRepository.findByRole(enumRole);

	        } catch (IllegalArgumentException e) {

	            throw new IllegalArgumentException("Invalid role provided: " + role);

	        }

	    }

	    @Override

	    public List<User> searchByNameOrEmail(String keyword) {

	        if (keyword == null || keyword.trim().isEmpty()) {

	            return userRepository.findAll(); // fallback to all users

	        }

	        return userRepository.searchByNameOrEmail(keyword.trim());

	    }
 
 
}

 