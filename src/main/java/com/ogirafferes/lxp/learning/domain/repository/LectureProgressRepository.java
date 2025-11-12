package com.ogirafferes.lxp.learning.domain.repository;

import com.ogirafferes.lxp.learning.domain.model.LectureProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureProgressRepository extends JpaRepository<LectureProgress, Long> {

    // 특정 수강 등록(Enrollment)에 대한 전체 강의 진도 조회
    List<LectureProgress> findByEnrollmentId(Long enrollmentId);

    // 특정 수강과 강의에 대한 진도 조회
    Optional<LectureProgress> findByEnrollmentIdAndLectureId(Long enrollmentId, Long lectureId);

}
