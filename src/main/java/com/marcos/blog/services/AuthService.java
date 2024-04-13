package com.marcos.blog.services;

import com.marcos.blog.models.User;
import com.marcos.blog.payload.requests.LoginRequest;
import com.marcos.blog.payload.requests.UserRequest;
import com.marcos.blog.payload.response.AuthResponse;
import com.marcos.blog.repositories.UserRepository;
import com.marcos.blog.security.JwtUtils;
import com.marcos.blog.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, UserService userService, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with this username or email: %s", usernameOrEmail)));

        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }

    public ResponseEntity<AuthResponse> register(UserRequest request) {
        userService.createUser(request);

        Authentication authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password(), null);
        String accessToken = jwtUtils.createToken(authentication);

        return new ResponseEntity<>(new AuthResponse("User created successfully", accessToken), HttpStatus.OK);
    }

    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        Authentication authentication = this.authenticate(request.username(), request.password());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        return new ResponseEntity<>(new AuthResponse("Successfully logged", accessToken), HttpStatus.OK);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
}
