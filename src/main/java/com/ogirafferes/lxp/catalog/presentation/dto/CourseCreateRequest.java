package com.ogirafferes.lxp.catalog.presentation.dto;

import com.ogirafferes.lxp.catalog.domain.model.CourseStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CourseCreateRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private CourseStatus courseStatus;
    private Long categoryId;
    private Long instructorId;
}
