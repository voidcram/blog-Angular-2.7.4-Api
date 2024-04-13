package com.marcos.blog.payload.response;

public record AuthResponse(
        String message,
        String token
) {
}
