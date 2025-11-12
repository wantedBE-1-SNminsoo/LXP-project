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
    private Long commentId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static PostComment create(Long postId, Long authorId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
