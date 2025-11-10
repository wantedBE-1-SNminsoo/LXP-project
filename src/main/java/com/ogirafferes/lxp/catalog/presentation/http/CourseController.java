package com.ogirafferes.lxp.catalog.presentation.http;

import com.ogirafferes.lxp.catalog.application.CourseCatalogService;
import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.presentation.dto.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController                          // REST API 컨트롤러임 명시
@RequestMapping("/api/courses")           // URL Prefix
@RequiredArgsConstructor
public class CourseController {

    private final CourseCatalogService courseService;

    @PostMapping
    public CourseResponse createCourse(@RequestBody Course course) {
        return new CourseResponse(courseService.createCourse(course));
    }

    @GetMapping
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses().stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CourseResponse getCourseById(@PathVariable Long id) {
        return new CourseResponse(courseService.getCourseDetail(id));
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }
}
