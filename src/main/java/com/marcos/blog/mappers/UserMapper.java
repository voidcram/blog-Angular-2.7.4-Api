package com.marcos.blog.mappers;

import com.marcos.blog.models.User;
import com.marcos.blog.payload.requests.UserRequest;
import com.marcos.blog.payload.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse mapToDTO(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    public User mapToEntity(UserRequest userRequest) {
        return User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .build();
    }
}