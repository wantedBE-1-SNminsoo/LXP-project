package com.ogirafferes.lxp.sales.presentation.dto;


import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.identity.domain.model.User;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 장바구니 목록 응답 DTO
 */
@Getter
public class ResponseAddedCartItem {

    public final Long cartItemId;
    public final String courseTitle;
    public final String courseInstructor;
    public final BigDecimal coursePrice;

    @Builder
    private ResponseAddedCartItem(Long cartItemId, String courseTitle, String courseInstructor, BigDecimal coursePrice) {
        this.cartItemId = cartItemId;
        this.courseTitle = courseTitle;
        this.courseInstructor = courseInstructor;
        this.coursePrice = coursePrice;
    }

    public static ResponseAddedCartItem from(Long cartItemId, User user, Course course) {
        return ResponseAddedCartItem.builder()
                .cartItemId(cartItemId)
                .courseTitle(course.getTitle())
                .courseInstructor(user.getNickname())
                .coursePrice(course.getPrice())
                .build();

    }

}
