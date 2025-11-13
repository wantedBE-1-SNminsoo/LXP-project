package com.ogirafferes.lxp.global.common.http;

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

    @GetMapping("/my_page") // enrollment들을 나열하고, 해당 각각의 enrollment로 course detail 페이지로 이동하는 링크 제공
    public String listUserEnrollments(Model model,
                                      @AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUserId();

        List<EnrollmentWithProgressResponse> enrollmentsWithProgress =
                learningService.getUserEnrollmentsWithProgress(userId);

        model.addAttribute("enrollments", enrollmentsWithProgress);

        // users my Posts list
        return "my_page";
    }
}
