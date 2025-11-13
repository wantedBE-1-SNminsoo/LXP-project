package com.ogirafferes.lxp.catalog.presentation.http;

import com.ogirafferes.lxp.catalog.application.CourseCatalogService;
import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.presentation.dto.CourseCreateRequest;
import com.ogirafferes.lxp.identity.application.adapter.CustomUserPrincipal;
import com.ogirafferes.lxp.learning.application.LearningService;
import com.ogirafferes.lxp.learning.domain.model.Enrollment;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseCatalogService courseCatalogService;
    private final LearningService learningService;

    // 전체 강좌 목록 페이지
//    @GetMapping
//    public String list(Model model) { // 해당 페이지는 admin 에 적합한 페이지
//        List<Course> courses = courseCatalogService.getAllCourses();
//        model.addAttribute("courses", courses);
//        return "catalog/course-list";
//    }

    // 활성 강좌 목록 페이지
    @GetMapping
    public String listActive(Model model) {
        List<Course> courses = courseCatalogService.getActiveCourses();
        model.addAttribute("courses", courses);
        return "catalog/course-list-active";
    }

    // 강좌 상세 페이지 (강의 목록 포함)
    @GetMapping("/{courseId}")
    public String detail(@PathVariable Long courseId,
                         @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal,
                         Model model) {
        Course course = courseCatalogService.getCourseWithLectures(courseId);
        model.addAttribute("course", course);

        Long userId = customUserPrincipal.getUserId();

        // 반드시 isEnrolled를 false로 설정 (null이 되지 않도록)
        boolean isEnrolled = false;

        // 현재 사용자가 해당 강좌에 수강 중인지 여부 확인 로직 추가 필요
        // 해당 강좌를 수강 중일 경우, isEnrolled를 true로 설정
        // 해당 강좌의 각 lecture 에 대해 complete로 만드는 button 활성화 여부 결정
        Optional<Enrollment> enrollment = learningService.getEnrollment(userId, courseId);
        if (enrollment.isPresent()) {
            isEnrolled = true;
            Enrollment enroll = enrollment.get();
            model.addAttribute("enrollment", enrollment.get());

            Set<Long> completedLectureIds = enroll.getProgresses().stream()
                    .map(progress -> progress.getLecture().getId())
                    .collect(Collectors.toSet());
            model.addAttribute("completedLectureIds", completedLectureIds);
        }
        model.addAttribute("isEnrolled", isEnrolled);

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
