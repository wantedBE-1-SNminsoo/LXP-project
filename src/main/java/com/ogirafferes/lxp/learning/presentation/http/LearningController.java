package com.ogirafferes.lxp.learning.presentation.http;

import com.ogirafferes.lxp.learning.application.LearningService;
import com.ogirafferes.lxp.learning.domain.model.Enrollment;
import com.ogirafferes.lxp.learning.presentation.dto.EnrollmentWithProgressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("enrollment")
@RequiredArgsConstructor
public class LearningController {

    private final LearningService learningService;

    @GetMapping("/my")
    public String listUserEnrollments(Model model, Principal principal) {
        Long userId = Long.parseLong(principal.getName()); // 인증정보에서 userId 추출 가정
        List<Enrollment> enrollments = learningService.getUserEnrollments(userId);
        model.addAttribute("enrollments", enrollments);
        return "learning/enrollment-list";
    }

    @PostMapping("/register")
    public String registerEnrollment(Long courseId, Principal principal, Model model) {
        Long userId = Long.parseLong(principal.getName()); // 인증정보에서 userId 추출 가정
        try {
            learningService.registerEnrollment(userId, courseId);
            return "redirect:/enrollment/my";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "learning/enrollment-error";
        }
    }

    @GetMapping("/{enrollmentId}/progress")
    public String viewEnrollmentProgress(@PathVariable Long enrollmentId, Model model, Principal principal) {
        Long userId = Long.parseLong(principal.getName()); // 인증정보에서 userId 추출 가정
        EnrollmentWithProgressResponse enrollmentProgress = learningService.getEnrollmentProgress(enrollmentId,userId);

        model.addAttribute("progress", enrollmentProgress);
        return "learning/progress-detail";
    }

    @GetMapping("/{enrollmentId}")
    public String viewEnrollmentDetail(@PathVariable Long enrollmentId, Model model, Principal principal) {
        Long userId = Long.parseLong(principal.getName()); // 인증정보에서 userId 추출 가정
        Enrollment enrollment = learningService.getEnrollmentById(enrollmentId);

        if (!enrollment.getUser().getUserId().equals(userId)) {
            model.addAttribute("error", "접근 권한이 없습니다.");
            return "learning/enrollment-error";
        }

        model.addAttribute("enrollment", enrollment);
        return "learning/enrollment-detail";
    }

}
