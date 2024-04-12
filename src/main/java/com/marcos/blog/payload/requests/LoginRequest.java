package com.marcos.blog.payload.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotEmpty()
        @Size(min = 3, max = 20)
        String name,

        @NotEmpty
        @Size(min = 3, max = 20)
        String password,

        @NotEmpty()
        @Email()
        String email
) {}
