package com.marcos.blog.mappers;

import com.marcos.blog.exceptions.ResourceNotFoundException;
import com.marcos.blog.models.Post;
import com.marcos.blog.models.User;
import com.marcos.blog.payload.requests.PostRequest;
import com.marcos.blog.payload.response.PostResponse;
import com.marcos.blog.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PostMapper {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public PostMapper(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public PostResponse mapToDTO(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                userMapper.mapToDTO(post.getUser()),
                post.getUpdatedAt(),
                post.getCreatedAt()
        );
    }

    public Post mapToEntity(PostRequest postRequest) {
        UUID userId = postRequest.userId();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return Post.builder()
                .title(postRequest.title())
                .body(postRequest.body())
                .user(user)
                .build();
    }
}
