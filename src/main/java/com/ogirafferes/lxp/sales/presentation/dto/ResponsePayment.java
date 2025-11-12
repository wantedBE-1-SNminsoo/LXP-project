package com.ogirafferes.lxp.sales.presentation.dto;

import com.ogirafferes.lxp.sales.domain.model.Payment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 임시 장바구니 결제 결과 DTO
 */
@Getter
public class ResponsePayment {

    private String orderNumber;
    private String paymentMethod;
    private String paymentProvider;

    private String transactionId;

    private String paymentStatus;

    private LocalDateTime paidAt;

    @Builder
    public ResponsePayment(String orderNumber, String paymentMethod, String paymentProvider, String transactionId, String paymentStatus, LocalDateTime paidAt) {
        this.orderNumber = orderNumber;
        this.paymentMethod = paymentMethod;
        this.paymentProvider = paymentProvider;
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
        this.paidAt = paidAt;
    }

    public static ResponsePayment of(Payment payment) {
        return ResponsePayment.builder()
                .orderNumber(payment.getOrderNumber())
                .paymentMethod(payment.getPaymentMethod().toString())
                .paymentProvider(payment.getPaymentProvider())
                .transactionId(payment.getTransactionId())
                .paymentStatus(payment.getPaymentStatus().toString())
                .paidAt(payment.getPaidAt())
                .build();
    }
}
