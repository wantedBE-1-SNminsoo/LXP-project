package com.ogirafferes.lxp.community.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_comments")
@Getter
@NoArgsConstructor
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    public static PostComment create(Long postId, Long authorId, String content) {
        PostComment postComment = new PostComment();

        postComment.postId = postId;
        postComment.authorId = authorId;
        postComment.content = content;
        postComment.createdAt = LocalDateTime.now();
        postComment.updatedAt = LocalDateTime.now();

        return postComment;
    }

    public void updateContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

}
