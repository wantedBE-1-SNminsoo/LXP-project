package com.ogirafferes.lxp.community.application;

import com.ogirafferes.lxp.community.domain.model.Post;
import com.ogirafferes.lxp.community.domain.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityService {

    private final PostRepository postRepository;

    public CommunityService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Post create(Long userId, Post post){

        post.setAuthorId(userId);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Transactional
    public Post update(Long id, Post newPost){

        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수가 없습니다." + id));

        post.setPostType(post.getPostType());
        post.setTitle(post.getTitle());
        post.setContent(post.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        return post;

    }

}
