package com.ogirafferes.lxp.catalog.application;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.domain.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service                      // 비즈니스 로직 계층임 명시
@RequiredArgsConstructor      // 생성자 주입 자동 생성 (Lombok)
public class CourseCatalogService {

    private final CourseRepository courseRepository;

    // 신규 강좌 등록
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // 전체 강좌 조회
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 강좌 상세 조회
    public Course getCourseDetail(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌입니다."));
    }

    // 강좌 삭제
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }
}
