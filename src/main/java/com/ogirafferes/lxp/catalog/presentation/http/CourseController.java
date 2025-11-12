package com.ogirafferes.lxp.catalog.presentation.http;

import com.ogirafferes.lxp.catalog.application.CourseCatalogService;
import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.presentation.dto.CourseCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseCatalogService courseCatalogService;

    // 전체 강좌 목록 페이지
    @GetMapping
    public String list(Model model) {
        List<Course> courses = courseCatalogService.getAllCourses();
        model.addAttribute("courses", courses);
        return "catalog/course-list";
    }

    // 활성 강좌 목록 페이지
    @GetMapping("/active")
    public String listActive(Model model) {
        List<Course> courses = courseCatalogService.getActiveCourses();
        model.addAttribute("courses", courses);
        return "catalog/course-list-active";
    }

    // 강좌 상세 페이지 (강의 목록 포함)
    @GetMapping("/{courseId}")
    public String detail(@PathVariable Long courseId, Model model) {
        Course course = courseCatalogService.getCourseWithLectures(courseId);
        model.addAttribute("course", course);
        return "catalog/course-detail";
    }

    // 신규 강좌 등록 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("courseRequest", new CourseCreateRequest());
        return "catalog/course-form";
    }

    // 신규 강좌 등록 처리
    @PostMapping
    public String create(@ModelAttribute CourseCreateRequest courseRequest) {
        Course course = courseCatalogService.createCourse(courseRequest);
        return "redirect:/courses/" + course.getId();
    }

    // 강좌 수정 폼
    @GetMapping("/{courseId}/edit")
    public String editForm(@PathVariable Long courseId, Model model) {
        Course course = courseCatalogService.getCourseDetail(courseId);
        model.addAttribute("course", course);
        return "catalog/course-edit-form";
    }

    // 강좌 수정 처리
    @PostMapping("/{courseId}/edit")
    public String update(@PathVariable Long courseId,
                         @RequestParam String title,
                         @RequestParam String description,
                         @RequestParam BigDecimal price) {
        courseCatalogService.updateCourse(courseId, title, description, price);
        return "redirect:/courses/" + courseId;
    }
}
