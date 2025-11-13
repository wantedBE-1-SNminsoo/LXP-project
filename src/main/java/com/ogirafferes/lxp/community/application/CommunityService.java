package com.ogirafferes.lxp.community.application;

import com.ogirafferes.lxp.community.domain.model.Post;
import com.ogirafferes.lxp.community.domain.model.PostType;
import com.ogirafferes.lxp.community.domain.repository.PostRepository;
import com.ogirafferes.lxp.community.domain.repository.PostTypeRepository;
import com.ogirafferes.lxp.community.exception.PostNotFoundException;
import com.ogirafferes.lxp.community.exception.PostTypeNotFoundException;
import com.ogirafferes.lxp.community.exception.NotPostOwnerException;
import com.ogirafferes.lxp.community.exception.UserNotFoundException;
import com.ogirafferes.lxp.community.presentation.dto.CreatePostRequest;
import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.identity.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommunityService {

    private final PostRepository postRepository;
    private final PostTypeRepository postTypeRepository;
    private final UserRepository userRepository;

    public CommunityService(PostRepository postRepository, PostTypeRepository postTypeRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postTypeRepository = postTypeRepository;
        this.userRepository = userRepository;

    }

    @Transactional(readOnly =true)
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    @Transactional
    public Post create(Long userId, CreatePostRequest createPostRequest){
        User author = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));

        PostType postType = postTypeRepository.findById(createPostRequest.getPostTypeId())
                .orElseThrow(()-> new PostTypeNotFoundException(createPostRequest.getPostTypeId()));

        Post post = Post.create(author,createPostRequest.getTitle(),createPostRequest.getContent(), postType);

        return postRepository.save(post);
    }

    @Transactional
    public Post update(Long postId, Long loginUserId, CreatePostRequest createPostRequest){

        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId));

        if (!post.getAuthor().getId().equals(loginUserId)) {
            throw new NotPostOwnerException();
        }

        PostType postType = postTypeRepository.findById(createPostRequest.getPostTypeId())
                .orElseThrow(()->new PostTypeNotFoundException(createPostRequest.getPostTypeId()));

        post.update(createPostRequest.getTitle(),createPostRequest.getContent(),postType);
        return post;

    }

    @Transactional(readOnly = true)
    public Post findById(Long postId){
        return postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId));
    }

    @Transactional
    public void delete(Long postId, Long loginUserId) {

        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId));

        if (!post.getAuthor().getId().equals(loginUserId)) {
            throw new NotPostOwnerException();
        }

        postRepository.delete(post);
    }

    @Transactional
    public List<Post> findPostsByAuthor(User author){
        return postRepository.findAllByAuthor(author);
    }

}
