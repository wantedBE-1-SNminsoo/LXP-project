package com.ogirafferes.lxp.community.domain.model;

import com.ogirafferes.lxp.identity.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private PostComment parent;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    public static PostComment createRoot(Post post, User author, String content) {
        PostComment postComment = new PostComment();

        postComment.post = post;
        postComment.author = author;
        postComment.content = content;
        postComment.createdAt = LocalDateTime.now();
        postComment.updatedAt = LocalDateTime.now();

        return postComment;
    }

    public static PostComment replyTo(Post post, User author, PostComment parent,String content) {
        PostComment postComment = new PostComment();

        postComment.post = post;
        postComment.author = author;
        postComment.parent = parent;
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
