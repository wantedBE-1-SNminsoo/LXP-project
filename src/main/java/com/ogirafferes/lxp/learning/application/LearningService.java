package com.ogirafferes.lxp.learning.application;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.domain.model.CourseStatus;
import com.ogirafferes.lxp.catalog.domain.model.Lecture;
import com.ogirafferes.lxp.catalog.domain.repository.CourseRepository;
import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.identity.domain.repository.UserRepository;
import com.ogirafferes.lxp.learning.domain.model.Enrollment;
import com.ogirafferes.lxp.learning.domain.model.EnrollmentStatus;
import com.ogirafferes.lxp.learning.domain.repository.EnrollmentRepository;
import com.ogirafferes.lxp.learning.domain.repository.LectureProgressRepository;
import com.ogirafferes.lxp.learning.presentation.dto.EnrollmentWithProgressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LearningService {

    private final EnrollmentRepository enrollmentRepository;
    private final LectureProgressRepository lectureProgressRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public List<Enrollment> getUserEnrollments(Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }

    public Enrollment getEnrollment(Long userId, Long courseId) {
        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId).orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
    }

    public Enrollment getEnrollmentById(Long enrollmentId) {
        return enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
    }

    @Transactional
    public Enrollment registerEnrollment(Long userId, Long courseId) {
        // Check if the enrollment already exists
        Optional<Enrollment> existEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);

        if (existEnrollment.isPresent()) {
            throw new IllegalArgumentException("이미 등록된 강좌입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getCourseStatus() != CourseStatus.PUBLISHED) {
            throw new IllegalArgumentException("강좌가 공개 상태가 아닙니다.");
        }

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .enrollmentStatus(EnrollmentStatus.ACTIVE)
                .progressPercentage(0)
                .enrolledAt(LocalDateTime.now())
                .build();

        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void completeLecture(Long enrollmentId, Long lectureId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));

        if (enrollment.getEnrollmentStatus() != EnrollmentStatus.ACTIVE) {
            throw new IllegalStateException("수강을 진행중인 강의가 아닙니다");
        }

        Lecture lecture =
                enrollment.getCourse().getLectures().stream()
                        .filter(lec -> lec.getId().equals(lectureId))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Lecture not found in the course"));

        enrollment.completeLecture(lecture);
        enrollmentRepository.save(enrollment);
    }

    @Transactional(readOnly = true)
    public EnrollmentWithProgressResponse getEnrollmentProgress(Long enrollmentId, Long userId) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));

        // 권한 검증
        if (!enrollment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        return EnrollmentWithProgressResponse.response(enrollment);
    }

}
