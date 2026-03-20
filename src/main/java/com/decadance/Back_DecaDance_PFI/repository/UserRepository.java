package com.decadance.Back_DecaDance_PFI.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.decadance.Back_DecaDance_PFI.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}