package com.decadance.Back_DecaDance_PFI.service;

import com.decadance.Back_DecaDance_PFI.dto.request.UserRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.UserResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.User;
import com.decadance.Back_DecaDance_PFI.mapper.UserMapper;
import com.decadance.Back_DecaDance_PFI.repository.UserRepository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.decadance.Back_DecaDance_PFI.security.UserDetail;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("El nombre de usuario '" + request.username() + "' ya está en uso.");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("El email '" + request.email() + "' ya está registrado.");
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new UserDetail(user))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = getUserByIdOrThrow(id);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUsername(Long id, String newUsername) {
        User user = getUserByIdOrThrow(id);
        if (!user.getUsername().equals(newUsername) && userRepository.existsByUsername(newUsername)) {
            throw new RuntimeException("El nombre de usuario '" + newUsername + "' ya está en uso.");
        }
        user.setUsername(newUsername);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDTO updateUserImage(Long id, String image) {
        User user = getUserByIdOrThrow(id);
        user.setImage(image); 
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserByIdOrThrow(id);
        userRepository.delete(user);
    }


    private User getUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

}