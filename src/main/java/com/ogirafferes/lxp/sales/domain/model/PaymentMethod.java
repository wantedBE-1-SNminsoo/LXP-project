package com.ogirafferes.lxp.sales.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethod {

    MINSUPAY("민수 페이", "간편 결제");

    private final String description;
    private final String provider;

}
