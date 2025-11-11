package com.ogirafferes.lxp.catalog.application;

import com.ogirafferes.lxp.catalog.domain.model.*;
import com.ogirafferes.lxp.catalog.domain.repository.CategoryRepository;
import com.ogirafferes.lxp.catalog.domain.repository.CourseRepository;
import com.ogirafferes.lxp.catalog.domain.service.CourseDomainService;
import com.ogirafferes.lxp.catalog.presentation.dto.CourseCreateRequest;
import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.identity.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseCatalogService {

    private final CourseRepository courseRepository;
    private final CourseDomainService courseDomainService;
    private final CategoryRepository categoryRepository;

    // 강좌 전체 조회
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 강좌 상세 조회
    public Course getCourseDetail(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌입니다."));
    }

    @Transactional(readOnly = true)
    public Course getCourseWithLectures(Long courseId) {
        return courseRepository.findByIdWithLectures(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌입니다."));
    }

    @Transactional(readOnly = true)
    public List<Course> getActiveCourses() {
        return courseRepository.findAllByStatusWithDetails(CourseStatus.ACTIVE);
    }


    // 강좌 생성
    @Transactional
    public Course createCourse(CourseCreateRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        User instructor = UserRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new IllegalArgumentException("강사를 찾을 수 없습니다."));

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .courseStatus(request.getCourseStatus())
                .category(category)
                .instructor(instructor)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

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


    @Transactional
    public Course updateCourse(Long courseId, String title, String description, BigDecimal price) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌입니다."));

        course.updateInfo(title, description, price);
        return course;
    }


    @Transactional
    public void addLectureToCourse(Long courseId, Lecture lecture) {
        Course course = courseRepository.findByIdWithLectures(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌입니다."));

        course.addLecture(lecture);
    }



}
