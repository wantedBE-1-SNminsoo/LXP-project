package com.ogirafferes.lxp.catalog.presentation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureCreateRequest {
    private String title;
    private String contentUrl;
    private int lectureOrder;
}
