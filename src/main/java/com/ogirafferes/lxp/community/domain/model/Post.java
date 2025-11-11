package com.ogirafferes.lxp.community.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name ="posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "post_type_id")
    private Long postTypeId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Post() {}

    public static Post create(Long authorId, String title, String content, Long postTypeId){
        Post post = new Post();

        post.authorId = authorId;
        post.title = title;
        post.content = content;
        post.postTypeId = postTypeId;
        post.createdAt = LocalDateTime.now();
        post.updatedAt = LocalDateTime.now();
        return post;
    }

    public void update(String title, String content, Long postTypeId){
        this.title = title;
        this.content = content;
        this.postTypeId = postTypeId;
        this.updatedAt = LocalDateTime.now();
    }
}
