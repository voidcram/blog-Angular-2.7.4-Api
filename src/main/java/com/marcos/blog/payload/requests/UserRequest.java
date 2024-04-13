package com.marcos.blog.payload.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotEmpty()
        @Size(min = 3, max = 20)
        String username,

        @NotEmpty
        @Size(min = 3, max = 20)
        String password,

        @NotEmpty()
        @Email()
        String email
) {
}
