package com.ogirafferes.lxp.catalog.application;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.domain.repository.CourseRepository;
import com.ogirafferes.lxp.catalog.domain.service.CourseDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseCatalogService {

    private final CourseRepository courseRepository;
    private final CourseDomainService courseDomainService;

    // 강좌 전체 조회
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 강좌 상세 조회
    public Course getCourseDetail(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌입니다."));
    }

    // 강좌 생성
    @Transactional
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // 강좌 활성화 (도메인 서비스 활용)
    @Transactional
    public void activateCourse(Long courseId) {
        Course course = getCourseDetail(courseId);
        courseDomainService.activateCourse(course);
        courseRepository.save(course);
    }

    // 강좌 비활성화
    @Transactional
    public void deactivateCourse(Long courseId) {
        Course course = getCourseDetail(courseId);
        courseDomainService.deactivateCourse(course);
        courseRepository.save(course);
    }
}
