package com.ogirafferes.lxp.community.application;

import com.ogirafferes.lxp.community.domain.model.Post;
import com.ogirafferes.lxp.community.domain.model.PostComment;
import com.ogirafferes.lxp.community.domain.repository.PostCommentRepository;
import com.ogirafferes.lxp.community.domain.repository.PostRepository;
import com.ogirafferes.lxp.community.exception.NotParentCommentException;
import com.ogirafferes.lxp.community.exception.ParentPostMismatchException;
import com.ogirafferes.lxp.community.exception.PostNotFoundException;
import com.ogirafferes.lxp.community.exception.UserNotFoundException;
import com.ogirafferes.lxp.community.presentation.dto.CreateCommentRequest;
import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.identity.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(PostCommentRepository postCommentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.postCommentRepository = postCommentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createComment(CreateCommentRequest createCommentRequest, Long userId) {
        User author = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));

        Post post = postRepository.findById(createCommentRequest.getPostId()).orElseThrow(()-> new PostNotFoundException(createCommentRequest.getPostId()));

        PostComment parent = null;

        if(createCommentRequest.getParentCommentId()!=null) {
            Long parentId = createCommentRequest.getParentCommentId();
            parent = postCommentRepository.findById(createCommentRequest.getParentCommentId()).
                    orElseThrow(()-> new NotParentCommentException(parentId));

            if(!parent.getPost().getId().equals(post.getId())) {
                throw new ParentPostMismatchException(parent.getId(),post.getId());
            }
        }

        PostComment comment;
        if(parent == null){
            comment = PostComment.createRoot(post,author, createCommentRequest.getContent());
        } else {
            comment = PostComment.replyTo(post,author,parent,createCommentRequest.getContent());
        }
        postCommentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<PostComment> getCommentsByPostId(Long postId) {
        return postCommentRepository.findByPostIdOrderByCreatedAtAsc(postId);
    }

}
