package com.theraconnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.theraconnect.enums.Role;
import com.theraconnect.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	Optional<User> findFirstByEmail(String email);
	long countByRole(Role doctor);
	Optional<User> findByName(String fullname);
	
	List<User> findByRole(Role role);
	@Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<User> searchByNameOrEmail(@Param("keyword") String keyword);

}
