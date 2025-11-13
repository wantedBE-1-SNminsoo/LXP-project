package com.ogirafferes.lxp.catalog.domain.service;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.domain.model.CourseStatus;
import org.springframework.stereotype.Service;

@Service
public class CourseDomainService {

    // 강좌 활성화 정책 (비즈니스 규칙 검증 포함)
    public void activateCourse(Course course) {
        // 비즈니스 규칙: 강의가 1개 이상 있어야 활성화 가능
        if (course.getLectures().isEmpty()) {
            throw new IllegalStateException("강의가 없는 강좌는 활성화할 수 없습니다.");
        }
        course.changeStatus(CourseStatus.PUBLISHED);
    }

    // 강좌 비활성화 정책
    public void deactivateCourse(Course course) {
        course.changeStatus(CourseStatus.INACTIVE);
    }

    // 추가 복잡한 비즈니스 로직들을 여기에 배치
}
