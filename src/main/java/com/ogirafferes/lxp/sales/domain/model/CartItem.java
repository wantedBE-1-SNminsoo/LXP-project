package com.ogirafferes.lxp.sales.domain.model;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.identity.domain.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니 목록 Entity
 */
@Entity
@Table(name = "cart_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Builder
    private CartItem(User user, Course course) {
        this.user = user;
        this.course = course;
    }

    public static CartItem from(User user, Course course) {
        return CartItem.builder()
                .user(user)
                .course(course)
                .build();
    }
}
