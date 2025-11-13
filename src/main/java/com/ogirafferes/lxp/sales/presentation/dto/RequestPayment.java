package com.ogirafferes.lxp.sales.presentation.dto;

import com.ogirafferes.lxp.sales.domain.model.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class RequestPayment {

    private final Long userId;

    @NotNull(message = "결제할 품목이 없습니다.")
    private final List<Long> cartItemIds;

    @NotNull(message = "결제 방법을 선택해 주세요.")
    private final PaymentMethod paymentMethod;

    @NotBlank(message = "결제 종류를 입력해 주세요.")
    private final String paymentProvider;

    public RequestPayment(Long userId, List<Long> cartItemIds, PaymentMethod paymentMethod) {
        this.userId = userId;
        this.cartItemIds = cartItemIds;
        this.paymentMethod = paymentMethod;
        this.paymentProvider = paymentMethod.getProvider();
    }
}
