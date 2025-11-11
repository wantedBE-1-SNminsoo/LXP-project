package com.ogirafferes.lxp.catalog.domain.repository;

import com.ogirafferes.lxp.catalog.domain.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByCourseIdOrderByLectureOrderAsc(Long courseId);
}
