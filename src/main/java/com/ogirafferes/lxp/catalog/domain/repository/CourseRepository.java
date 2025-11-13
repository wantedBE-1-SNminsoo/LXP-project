package com.ogirafferes.lxp.catalog.domain.repository;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.domain.model.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // 수정: JOIN FETCH → LEFT JOIN FETCH (강의가 없어도 강좌 조회 가능)
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.lectures WHERE c.id = :id")
    Optional<Course> findByIdWithLectures(@Param("id") Long id);

    @Query("SELECT DISTINCT c FROM Course c " +
            "JOIN FETCH c.category " +
            "LEFT JOIN FETCH c.lectures " +
            "WHERE c.courseStatus = :status")
    List<Course> findAllByStatusWithDetails(@Param("status") CourseStatus status);

    @Query("SELECT DISTINCT c FROM Course c " +
            "LEFT JOIN FETCH c.lectures " +
            "WHERE c.id = :id")
    Optional<Course> findByIdWithLecturesAndCategory(@Param("id") Long id);


    // 강좌 제목으로 조회 (부분일치 검색 지원)
    List<Course> findByTitleContaining(String keyword);

    // 강사 ID로 강좌 조회
    List<Course> findByInstructorId(Long instructorId);


}
