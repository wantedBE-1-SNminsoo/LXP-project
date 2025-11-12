package com.ogirafferes.lxp.catalog.presentation.http;

import com.ogirafferes.lxp.catalog.application.CourseCatalogService;
import com.ogirafferes.lxp.catalog.application.LectureService;
import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.domain.model.Lecture;
import com.ogirafferes.lxp.catalog.presentation.dto.CourseCreateRequest;
import com.ogirafferes.lxp.catalog.presentation.dto.LectureCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/my/courses")
@RequiredArgsConstructor
public class MyCourseController {

    private final CourseCatalogService courseCatalogService;
    private final LectureService lectureService;

    // 내가 개설한 강좌 목록
    @GetMapping
    public String myList(Model model, Principal principal) {
        // Principal에서 사용자 ID 추출 (실제 인증 구현에 따라 조정)
        Long instructorId = Long.parseLong(principal.getName());
        List<Course> myCourses = courseCatalogService.getCoursesByInstructor(instructorId);
        model.addAttribute("courses", myCourses);
        return "catalog/my-course-list";
    }

    // 강좌 개설 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("courseRequest", new CourseCreateRequest());
        return "catalog/my-course-form";
    }

    // 강좌 개설 처리
    @PostMapping
    public String create(@ModelAttribute CourseCreateRequest request, Principal principal) {
        // 현재 로그인한 사용자를 강사로 설정
        Long instructorId = Long.parseLong(principal.getName());
        request.setInstructorId(instructorId);

        Course course = courseCatalogService.createCourse(request);
        return "redirect:/my/courses/" + course.getId();
    }

    // 내 강좌 상세 (강의 관리)
    @GetMapping("/{courseId}")
    public String detail(@PathVariable Long courseId, Model model, Principal principal) {
        Course course = courseCatalogService.getCourseWithLectures(courseId);

        // 본인 강좌인지 검증
        Long instructorId = Long.parseLong(principal.getName());
        if (!course.getInstructor().getUserId().equals(instructorId)) {
            throw new IllegalArgumentException("본인의 강좌만 관리할 수 있습니다.");
        }

        model.addAttribute("course", course);
        return "catalog/my-course-detail";
    }

    // 강의 추가 폼
    @GetMapping("/{courseId}/lectures/new")
    public String addLectureForm(@PathVariable Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("lectureRequest", new LectureCreateRequest());
        return "catalog/my-lecture-form";
    }

    // 강의 추가 처리
    @PostMapping("/{courseId}/lectures")
    public String addLecture(@PathVariable Long courseId,
                             @ModelAttribute LectureCreateRequest request) {
        lectureService.addLecture(courseId, request);
        return "redirect:/my/courses/" + courseId;
    }

    // 강의 삭제
    @PostMapping("/{courseId}/lectures/{lectureId}/delete")
    public String deleteLecture(@PathVariable Long courseId,
                                @PathVariable Long lectureId) {
        lectureService.deleteLecture(courseId, lectureId);
        return "redirect:/my/courses/" + courseId;
    }
}
