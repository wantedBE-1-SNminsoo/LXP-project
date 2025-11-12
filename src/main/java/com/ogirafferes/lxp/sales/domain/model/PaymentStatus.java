package com.ogirafferes.lxp.sales.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentStatus {
    COMPLETED("결제 성공"),
    FAILED("결제 실패"),
    PENDING("결제 대기");

    private final String description;
}
