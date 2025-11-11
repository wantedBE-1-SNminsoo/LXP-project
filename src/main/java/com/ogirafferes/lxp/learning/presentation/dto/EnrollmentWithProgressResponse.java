package com.ogirafferes.lxp.learning.presentation.dto;

import com.ogirafferes.lxp.learning.domain.model.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PUBLIC)
@Builder
public class EnrollmentWithProgressResponse {
    private Long enrollmentId;
    private Long courseId;
    private String courseTitle;

    private int completedLectureCount;  // 완료한 강의 수
    private int totalLectureCount;      // 전체 강의 수
    private int progressPercentage; // 진도율 (%)

    public static EnrollmentWithProgressResponse response(Enrollment enrollment) {
        return EnrollmentWithProgressResponse.builder()
                .enrollmentId(enrollment.getId())
                .courseId(enrollment.getCourse().getId())
                .courseTitle(enrollment.getCourse().getTitle())
                .completedLectureCount(enrollment.getCompletedLectureCount())
                .totalLectureCount(enrollment.getTotalLectureCount())
                .progressPercentage(enrollment.getProgressPercentage())
                .build();
    }
}
