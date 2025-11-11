package com.ogirafferes.lxp.catalog.presentation.dto;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.domain.model.CourseStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private CourseStatus courseStatus;  // Enum 타입으로 변경
    private LocalDateTime createdAt;

    public CourseResponse(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.price = course.getPrice();
        this.courseStatus = course.getCourseStatus();
        this.createdAt = course.getCreatedAt();
    }
}
