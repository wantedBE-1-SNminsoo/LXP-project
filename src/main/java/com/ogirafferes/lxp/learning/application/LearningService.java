package com.ogirafferes.lxp.learning.application;

import com.ogirafferes.lxp.learning.domain.model.Enrollment;
import com.ogirafferes.lxp.learning.domain.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningService {

    private final EnrollmentRepository enrollmentRepository;

    public List<Enrollment> getUserEnrollments(Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }

    public Enrollment getEnrollment(Long userId, Long courseId) {
        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
    }

    public Enrollment registerEnrollment(Enrollment enrollment) {
        // 필수 비즈니스 로직 처리 (중복 등록 방지, 상태 체크 등) 추가 가능
        return enrollmentRepository.save(enrollment);
    }
}
