package com.ogirafferes.lxp.catalog.presentation.dto;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String status;
    private String categoryName;
    private List<LectureResponse> lectures;

    public static CourseResponse from(Course course) {
        List<LectureResponse> lectures = course.getLectures().stream()
                .map(LectureResponse::from)
                .collect(Collectors.toList());

        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getPrice(),
                course.getCourseStatus().name(),
                course.getCategory() != null ? course.getCategory().getName() : null,
                lectures
        );
    }
}
