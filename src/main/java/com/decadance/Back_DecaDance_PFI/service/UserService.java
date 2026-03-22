package com.decadance.Back_DecaDance_PFI.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.decadance.Back_DecaDance_PFI.dto.request.UserRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.UserResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.User;

public interface UserService {

    User getUserByUsername(String name);
    ResponseEntity<User> getUserById(Long id);
    ResponseEntity<List<User>> getUsers();
 
    UserResponseDTO registerUser(UserRequestDTO request);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
