package com.ogirafferes.lxp.learning.presentation.http;

import com.ogirafferes.lxp.learning.application.LearningService;
import com.ogirafferes.lxp.learning.domain.model.Enrollment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/my/enrollments")
@RequiredArgsConstructor
public class LearningController {

    private final LearningService learningService;

    @GetMapping
    public String listUserEnrollments(Model model, Principal principal) {
        Long userId = Long.parseLong(principal.getName()); // 인증정보에서 userId 추출 가정
        List<Enrollment> enrollments = learningService.getUserEnrollments(userId);
        model.addAttribute("enrollments", enrollments);
        return "learning/enrollment-list";
    }
}
