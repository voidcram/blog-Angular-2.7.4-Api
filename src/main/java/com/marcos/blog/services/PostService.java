package com.marcos.blog.services;

import com.marcos.blog.exceptions.ResourceNotFoundException;
import com.marcos.blog.mappers.PostMapper;
import com.marcos.blog.models.Post;
import com.marcos.blog.models.User;
import com.marcos.blog.payload.requests.PostRequest;
import com.marcos.blog.payload.response.PostResponse;
import com.marcos.blog.repositories.PostRepository;
import com.marcos.blog.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<Post> posts = postRepository.findAll();

//      Map into response DTO
        List<PostResponse> postResponses = posts.stream()
                .map(postMapper::mapToDTO)
                .toList();

        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }

    public ResponseEntity<PostResponse> getPostById(UUID id) {
        Post savedPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return new ResponseEntity<>(postMapper.mapToDTO(savedPost), HttpStatus.OK);
    }

    public ResponseEntity<PostResponse> createPost(PostRequest postRequest) {

        //      Map request into entity and save to db
        Post post = postMapper.mapToEntity(postRequest);
        Post savedPost = postRepository.save(post);

        return new ResponseEntity<>(postMapper.mapToDTO(savedPost), HttpStatus.CREATED);
    }

    public ResponseEntity<PostResponse> updatePost(UUID id, PostRequest postRequest) {
        Post savedPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
//      Check if user id exists
        UUID userId = postRequest.userId();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

//      Update saved post values
        savedPost.setTitle(postRequest.title());
        savedPost.setBody(postRequest.body());
        savedPost.setUser(user);
        Post updatedPost = postRepository.save(savedPost);

        return new ResponseEntity<>(postMapper.mapToDTO(updatedPost), HttpStatus.OK);
    }

    public ResponseEntity<Void> deletePost(UUID id) {
        Post savedPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(savedPost);

        return ResponseEntity.noContent().build();
    }
}
