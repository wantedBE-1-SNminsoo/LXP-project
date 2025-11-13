package com.ogirafferes.lxp.learning.presentation.http;

import com.ogirafferes.lxp.identity.application.adapter.CustomUserPrincipal;
import com.ogirafferes.lxp.learning.application.LearningService;
import com.ogirafferes.lxp.learning.domain.model.Enrollment;
import com.ogirafferes.lxp.learning.presentation.dto.EnrollmentWithProgressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Currency;

@Controller
@RequestMapping("/enrollment")
@RequiredArgsConstructor
public class LearningController {

    private final LearningService learningService;

    @PostMapping("/{enrollmentId}/completeLecture/{lectureId}") // 해당 요청의 자연스러운 위치는 lecture 에서 complete button을 통해 redirect?
    public String completeLecture(@PathVariable Long enrollmentId, @PathVariable Long lectureId, @AuthenticationPrincipal CustomUserPrincipal principal, Model model) {
        long userId = principal.getUserId();
        try {
            Long courseId = learningService.completeLecture(enrollmentId, lectureId, userId);
            return "redirect:/courses/" + courseId;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "learning/enrollment-error";
        }
    }
}
