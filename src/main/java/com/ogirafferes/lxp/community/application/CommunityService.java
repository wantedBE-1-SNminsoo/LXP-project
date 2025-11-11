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
    public Post update(Long postId, Long loginUserId, Post newPost){

        Post post = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수가 없습니다." + postId));

        if (!post.getAuthorId().equals(loginUserId)) {
            throw new IllegalStateException("본인 글만 수정할 수 있습니다.");
        }

        post.setPostType(newPost.getPostType());
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        return post;

    }

    @Transactional
    public void delete(Long postId, Long loginUserId) {

        Post post = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수가 없습니다." + postId));

        if (!post.getAuthorId().equals(loginUserId)) {
            throw new IllegalStateException("본인 글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

}
