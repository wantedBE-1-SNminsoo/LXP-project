package com.ogirafferes.lxp.catalog.presentation.dto;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String courseStatus;  // 수정된 필드명 반영
    private LocalDateTime createdAt;

    public CourseResponse(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.price = course.getPrice();
        this.courseStatus = course.getCourseStatus();
        this.createdAt = course.getCreatedAt();
    }

    // Getter 메서드 필요 시 추가
}
