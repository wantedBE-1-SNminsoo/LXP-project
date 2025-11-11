package com.ogirafferes.lxp.catalog.presentation.http;

import com.ogirafferes.lxp.catalog.application.CourseCatalogService;
import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.presentation.dto.CourseCreateRequest;
import com.ogirafferes.lxp.catalog.presentation.dto.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
        model.addAttribute("courseRequest", new CourseCreateRequest());
        return "catalog/course-form";
    }

    // 신규 강좌 등록 처리
    @PostMapping
    public String create(@ModelAttribute CourseCreateRequest courseRequest) {
        courseCatalogService.createCourse(courseRequest);
        return "redirect:/courses";
    }

    // 강좌 수정
    @PutMapping("/{courseId}")
    public String update(@PathVariable Long courseId,
                         @RequestParam String title,
                         @RequestParam String description,
                         @RequestParam BigDecimal price) {
        courseCatalogService.updateCourse(courseId, title, description, price);
        return "redirect:/courses/" + courseId;
    }

    // 강좌 상세 조회 (JSON)
    @GetMapping("/{courseId}/details")
    @ResponseBody
    public CourseResponse getDetails(@PathVariable Long courseId) {
        Course course = courseCatalogService.getCourseWithLectures(courseId);
        return CourseResponse.from(course);
    }

    // 활성 강좌 목록 조회 (JSON)
    @GetMapping("/active")
    @ResponseBody
    public List<CourseResponse> getActiveCourses() {
        List<Course> courses = courseCatalogService.getActiveCourses();
        return courses.stream()
                .map(CourseResponse::from)
                .collect(Collectors.toList());
    }


}
