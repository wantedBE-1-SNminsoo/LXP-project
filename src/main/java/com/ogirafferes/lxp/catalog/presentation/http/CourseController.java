package com.ogirafferes.lxp.catalog.presentation.http;

import com.ogirafferes.lxp.catalog.application.CourseCatalogService;
import com.ogirafferes.lxp.catalog.domain.model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseCatalogService courseCatalogService;

    // 강좌 목록 페이지
    @GetMapping
    public String list(Model model) {
        List<Course> courses = courseCatalogService.getAllCourses();
        model.addAttribute("courses", courses);
        return "catalog/course-list";
    }

    // 강좌 상세 페이지
    @GetMapping("/{courseId}")
    public String detail(@PathVariable Long courseId, Model model) {
        Course course = courseCatalogService.getCourseDetail(courseId);
        model.addAttribute("course", course);
        return "catalog/course-detail";
    }

    // 신규 강좌 등록 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        return "catalog/course-form";
    }

    // 신규 강좌 등록 처리
    @PostMapping
    public String create(@ModelAttribute Course course) {
        courseCatalogService.createCourse(course);
        return "redirect:/courses";
    }
}
