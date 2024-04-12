package com.marcos.blog.payload.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PostRequest(
        @NotEmpty
        @Size(min = 5, max = 20)
        String title,

        @NotEmpty
        @Size(min = 50, max = 2000)
        String description,

        @NotNull
        UUID userId
) {
}
