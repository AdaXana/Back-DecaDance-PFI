package com.decadance.Back_DecaDance_PFI.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import com.decadance.Back_DecaDance_PFI.dto.request.UserRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO dto);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUsername(Long id, String newUsername);
    UserResponseDTO updateUserImage(Long id, MultipartFile file);
    void deleteUser(Long id);
 
}