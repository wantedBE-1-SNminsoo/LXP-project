package com.ogirafferes.lxp.catalog.application;

import com.ogirafferes.lxp.catalog.domain.model.Category;
import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.domain.model.CourseStatus;
import com.ogirafferes.lxp.catalog.domain.model.Lecture;
import com.ogirafferes.lxp.catalog.domain.repository.CategoryRepository;
import com.ogirafferes.lxp.catalog.domain.repository.CourseRepository;
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
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;  // 주입받은 인스턴스

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

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
        return courseRepository.findAllByStatusWithDetails(CourseStatus.PUBLISHED);
    }

    // 강좌 생성 (수정됨)
    @Transactional
    public Course createCourse(CourseCreateRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        // 수정: userRepository 인스턴스 사용 (소문자 시작)
        User instructor = userRepository.findById(request.getInstructorId())
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

    @Transactional
    public Course updateCourse(Long courseId, String title, String description, BigDecimal price) {
        Course course = getCourseDetail(courseId);
        course.updateInfo(title, description, price);
        return course;
    }

    @Transactional
    public void addLectureToCourse(Long courseId, Lecture lecture) {
        Course course = getCourseWithLectures(courseId);
        course.addLecture(lecture);
    }
    // 강사별 강좌 목록 조회
    @Transactional(readOnly = true)
    public List<Course> getCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }


}
