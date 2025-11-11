package com.ogirafferes.lxp.learning.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnrollmentRequest {
    private Long courseId;
    private Long userId;
    private Long paymentId;
}
