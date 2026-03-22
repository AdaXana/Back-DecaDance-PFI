package com.decadance.Back_DecaDance_PFI.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.decadance.Back_DecaDance_PFI.dto.request.LoginRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.UserRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.UserResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.User;
import com.decadance.Back_DecaDance_PFI.mapper.UserMapper;
import com.decadance.Back_DecaDance_PFI.repository.UserRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User> getUserById(Long id) {
        User user = userRepository.findById(id).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public User getUserByUsername(String name) {
        User user = userRepository.findByUsername(name).get();
        return user;
    }

    @Override
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new UserDetail(user))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));
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


}