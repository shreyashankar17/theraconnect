package com.theraconnect.service;

import java.util.List;
import java.util.Optional;

import com.theraconnect.model.User;

public interface UserService {
	User createUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
    Optional<User> getUserByEmail(String email);
	User save(User user);
	List<User> getUsersByRole(String role);
	List<User> searchByNameOrEmail(String keyword);

}
