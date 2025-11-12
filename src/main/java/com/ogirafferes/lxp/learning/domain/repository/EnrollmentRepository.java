package com.ogirafferes.lxp.learning.domain.repository;

import com.ogirafferes.lxp.learning.domain.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // 사용자 ID로 해당 사용자의 전체 수강 목록 조회
    List<Enrollment> findByUserId(Long userId);

    // 사용자 ID와 강좌 ID로 특정 수강 정보 조회
    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);
}
