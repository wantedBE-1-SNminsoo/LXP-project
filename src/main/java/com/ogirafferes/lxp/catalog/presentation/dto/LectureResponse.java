package com.ogirafferes.lxp.catalog.presentation.dto;

import com.ogirafferes.lxp.catalog.domain.model.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureResponse {
    private Long id;
    private String title;
    private String contentUrl;
    private int lectureOrder;

    public static LectureResponse from(Lecture lecture) {
        return new LectureResponse(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getContentUrl(),
                lecture.getLectureOrder()
        );
    }
}
