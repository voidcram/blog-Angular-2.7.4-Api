package com.marcos.blog.payload.response;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email
) {
}