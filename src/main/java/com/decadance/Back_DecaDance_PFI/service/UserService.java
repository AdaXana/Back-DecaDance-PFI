package com.decadance.Back_DecaDance_PFI.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.decadance.Back_DecaDance_PFI.dto.request.LoginRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.UserRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO request);
    UserResponseDTO login(LoginRequestDTO request);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
