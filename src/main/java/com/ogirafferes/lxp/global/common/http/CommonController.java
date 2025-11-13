package com.ogirafferes.lxp.global.common.http;

import com.ogirafferes.lxp.catalog.application.CourseCatalogService;
import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.identity.application.adapter.CustomUserPrincipal;
import com.ogirafferes.lxp.learning.application.LearningService;
import com.ogirafferes.lxp.learning.domain.model.Enrollment;
import com.ogirafferes.lxp.learning.presentation.dto.EnrollmentWithProgressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class CommonController {

    private final LearningService learningService;
    private final CourseCatalogService courseCatalogService;

    @GetMapping("/my_page") // enrollment들을 나열하고, 해당 각각의 enrollment로 course detail 페이지로 이동하는 링크 제공
    public String listUserEnrollments(Model model,
                                      @AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUserId();
        List<EnrollmentWithProgressResponse> enrollmentsWithProgress =
                learningService.getUserEnrollmentsWithProgress(userId);

        // 수강중인 강좌 목록을 모델에 추가
        model.addAttribute("enrollments", enrollmentsWithProgress);

        // 내가 개설한 강좌 목록
        List<Course> myCourses = courseCatalogService.getCoursesByInstructor(userId);
        model.addAttribute("myCourses", myCourses);


        // users my Posts list
        return "my_page";
    }
}
