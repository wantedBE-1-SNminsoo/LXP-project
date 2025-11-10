package com.ogirafferes.lxp.catalog.presentation.dto;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String status;
    private LocalDateTime createdAt;

    public CourseResponse(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.price = course.getPrice();
        this.status = course.getStatus();
        this.createdAt = course.getCreatedAt();
    }
}
