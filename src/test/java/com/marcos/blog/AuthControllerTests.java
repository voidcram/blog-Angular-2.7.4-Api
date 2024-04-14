package com.marcos.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcos.blog.payload.requests.LoginRequest;
import com.marcos.blog.payload.requests.UserRequest;
import com.marcos.blog.payload.response.AuthResponse;
import com.marcos.blog.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Test
    void testCreateUserLoginGetTokenAndGetProtectedResource() throws Exception {
        String username = "Paquito";
        String email = "paquito@gmail.com";
        String password = "123";

        // Register a new user
        UserRequest userRequest = new UserRequest(username, password, email);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk());

        // Prepare login request
        LoginRequest loginRequest = new LoginRequest(username, password);

        // Login with created user
        MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // Extract token from login response
        String loginResponseJson = loginResult.getResponse().getContentAsString();
        AuthResponse authResponse = objectMapper.readValue(loginResponseJson, AuthResponse.class);
        String token = authResponse.token();

        // Use the obtained token to make authenticated request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}