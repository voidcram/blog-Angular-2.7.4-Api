package com.marcos.blog.payload.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostResponse(
        UUID id,
        String title,
        String description,
        UserResponse user,

        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {
}
