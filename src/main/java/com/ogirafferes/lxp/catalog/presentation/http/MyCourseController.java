package com.ogirafferes.lxp.catalog.presentation.http;

import com.ogirafferes.lxp.catalog.application.CourseCatalogService;
import com.ogirafferes.lxp.catalog.application.LectureService;
import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.domain.model.CourseStatus;
import com.ogirafferes.lxp.catalog.domain.model.Lecture;
import com.ogirafferes.lxp.catalog.domain.service.CourseDomainService;
import com.ogirafferes.lxp.catalog.presentation.dto.CourseCreateRequest;
import com.ogirafferes.lxp.catalog.presentation.dto.LectureCreateRequest;
import com.ogirafferes.lxp.identity.application.adapter.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final CourseDomainService courseDomainService;

    // 내가 개설한 강좌 목록
    @GetMapping
    public String myList(Model model, @AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
        // Principal에서 사용자 ID 추출 (실제 인증 구현에 따라 조정)
        Long instructorId = userPrincipal.getUserId();
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
    public String create(@ModelAttribute CourseCreateRequest request, @AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
        // 현재 로그인한 사용자를 강사로 설정
        Long instructorId = userPrincipal.getUserId();
        request.setInstructorId(instructorId);

        Course course = courseCatalogService.createCourse(request);
        return "redirect:/my/courses/" + course.getId();
    }

    // 내 강좌 상세 (강의 관리)
    @GetMapping("/{courseId}")
    public String detail(@PathVariable Long courseId, @AuthenticationPrincipal CustomUserPrincipal userPrincipal, Model model) {
        Course course = courseCatalogService.getCourseWithLectures(courseId);

        // 본인 강좌인지 검증
        Long instructorId = userPrincipal.getUserId();
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new IllegalArgumentException("본인의 강좌만 관리할 수 있습니다.");
        }

        model.addAttribute("course", course);
        return "catalog/my-course-detail";
    }

    @PostMapping("/{courseId}/change-status") // 해당 서비스 button으로 호출
    public String changeCourseStatus(@PathVariable Long courseId,
                                     @AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
        Course course = courseCatalogService.getCourseDetail(courseId);
        Long instructorId = userPrincipal.getUserId();
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new IllegalArgumentException("본인의 강좌만 관리할 수 있습니다.");
        }
        if (course.getCourseStatus() != CourseStatus.PUBLISHED) {
            courseDomainService.activateCourse(course);
        } else  {
            courseDomainService.deactivateCourse(course);
        }
        return "redirect:/my/courses/" + courseId;
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
