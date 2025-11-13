package com.ogirafferes.lxp.community.domain.model;

import com.ogirafferes.lxp.identity.domain.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="posts")
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_type_id", nullable = false)
    private PostType postType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "post",
            // **** 2. 외래 키 제약 조건 해결을 위한 설정 ****
            // 게시글(부모) 삭제 시 댓글(자식)도 자동으로 삭제 (CascadeType.REMOVE)
            cascade = CascadeType.REMOVE,
            // List에서 Comment를 제거하면 DB에서도 삭제되도록 설정
            orphanRemoval = true
    )
    private List<PostComment> comments;

    public static Post create(User author, String title, String content, PostType postType){
        Post post = new Post();

        post.author = author;
        post.title = title;
        post.content = content;
        post.postType = postType;
        post.createdAt = LocalDateTime.now();
        post.updatedAt = LocalDateTime.now();
        return post;
    }

    public void update(String title, String content, PostType postType){
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.updatedAt = LocalDateTime.now();
    }
}
