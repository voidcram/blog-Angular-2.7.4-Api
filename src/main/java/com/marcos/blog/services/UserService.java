package com.marcos.blog.services;

import com.marcos.blog.exceptions.DuplicateResourceException;
import com.marcos.blog.exceptions.ResourceNotFoundException;
import com.marcos.blog.models.User;
import com.marcos.blog.payload.requests.UserRequest;
import com.marcos.blog.payload.response.UserResponse;
import com.marcos.blog.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();

//      Map into response DTO
        List<UserResponse> userResponses = users.stream()
                .map(this::mapToDTO)
                .toList();

        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> getUserById(UUID id) {
        User savedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return new ResponseEntity<>(mapToDTO(savedUser), HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
        if (userRepository.existsUserByUsername(userRequest.username())) {
            throw new DuplicateResourceException("Username already taken");
        }

//      Build user from request DTO
        User user = mapToEntity(userRequest);
        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(mapToDTO(savedUser), HttpStatus.CREATED);
    }

    public ResponseEntity<UserResponse> updateUser(UUID id, UserRequest userRequest) {
        User savedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        savedUser.setUsername(userRequest.username());
        savedUser.setEmail(userRequest.email());
        savedUser.setPassword(passwordEncoder.encode(userRequest.password()));

        User updatedUser = userRepository.save(savedUser);
        return new ResponseEntity<>(mapToDTO(updatedUser), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUser(UUID id) {
        User savedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(savedUser);

        return ResponseEntity.noContent().build();
    }

    // Mappers
    private UserResponse mapToDTO(User user) {
        return new UserResponse(user.getId(), user.getUsername());
    }

    private User mapToEntity(UserRequest userRequest) {
        return User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();
    }
}
