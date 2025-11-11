package com.ogirafferes.lxp.catalog.domain.service;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import org.springframework.stereotype.Service;

@Service
public class LectureDomainService {

    // 강의 순서 중복 검증
    public void validateLectureOrder(Course course, int order) {
        boolean isDuplicate = course.getLectures().stream()
                .anyMatch(lecture -> lecture.getLectureOrder() == order);

        if (isDuplicate) {
            throw new IllegalArgumentException("이미 존재하는 강의 순서입니다.");
        }
    }

    // 강의 개수 제한 검증 (필요 시)
    public void validateMaxLectures(Course course, int maxLectures) {
        if (course.getLectures().size() >= maxLectures) {
            throw new IllegalArgumentException("강의 개수 제한을 초과했습니다.");
        }
    }
}
