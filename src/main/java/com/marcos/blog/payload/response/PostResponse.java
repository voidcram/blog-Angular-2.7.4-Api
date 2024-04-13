package com.marcos.blog.payload.response;

import java.util.UUID;

public record PostResponse(
        UUID id,
        String title,
        String description,
        UserResponse user
) {
}
