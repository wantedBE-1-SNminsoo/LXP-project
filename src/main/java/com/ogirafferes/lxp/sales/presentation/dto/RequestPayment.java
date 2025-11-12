package com.ogirafferes.lxp.sales.presentation.dto;

import com.ogirafferes.lxp.sales.domain.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RequestPayment {

    private Long userId;
    private List<Long> cartItemIds;

    private PaymentMethod paymentMethod;
    private String paymentProvider;

}
