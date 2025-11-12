package com.ogirafferes.lxp.sales.presentation.dto;

import com.ogirafferes.lxp.sales.domain.model.PaymentMethod;
import lombok.Getter;

import java.util.List;

@Getter
public class RequestPayment {

    private final Long userId;
    private final List<Long> cartItemIds;

    private final PaymentMethod paymentMethod;
    private final String paymentProvider;

    public RequestPayment(Long userId, List<Long> cartItemIds, PaymentMethod paymentMethod) {
        this.userId = userId;
        this.cartItemIds = cartItemIds;
        this.paymentMethod = paymentMethod;
        this.paymentProvider = paymentMethod.getProvider();
    }
}
