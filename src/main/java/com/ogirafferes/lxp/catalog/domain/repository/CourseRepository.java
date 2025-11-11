package com.ogirafferes.lxp.catalog.domain.repository;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // 강좌 제목으로 조회 (부분일치 검색 지원)
    List<Course> findByTitleContaining(String keyword);
}
