package com.ogirafferes.lxp.global;

import com.ogirafferes.lxp.catalog.application.CourseCatalogService;
import com.ogirafferes.lxp.catalog.domain.model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor // CourseCatalogService를 주입받기 위해 추가
public class HomeController {

    private final CourseCatalogService courseCatalogService; // 의존성 주입

    @GetMapping("/")
    public String home(Model model) { // Model 파라미터 추가
        // 활성 강좌 목록을 조회
        List<Course> activeCourses = courseCatalogService.getActiveCourses();

        // 모델에 'courses'라는 이름으로 데이터를 담아 뷰로 전달
        model.addAttribute("courses", activeCourses);

        // 'index.html' 템플릿을 렌더링
        return "index";
    }
}
