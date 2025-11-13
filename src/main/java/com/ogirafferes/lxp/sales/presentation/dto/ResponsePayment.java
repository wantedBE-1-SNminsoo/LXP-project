package com.ogirafferes.lxp.sales.presentation.dto;

import com.ogirafferes.lxp.sales.domain.model.Payment;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 임시 장바구니 결제 결과 DTO
 */
@Getter
public class ResponsePayment {

    private final String orderNumber;
    private final String paymentMethod;
    private final String paymentProvider;
    private final BigDecimal totalAmount;

    private final String transactionId;

    private final String paymentStatus;

    private final LocalDateTime paidAt;

    @Builder
    public ResponsePayment(String orderNumber, String paymentMethod, String paymentProvider, BigDecimal totalAmount, String transactionId, String paymentStatus, LocalDateTime paidAt) {
        this.orderNumber = orderNumber;
        this.paymentMethod = paymentMethod;
        this.paymentProvider = paymentProvider;
        this.totalAmount = totalAmount;
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
        this.paidAt = paidAt;
    }

    public static ResponsePayment of(Payment payment) {
        return ResponsePayment.builder()
                .orderNumber(payment.getOrderNumber())
                .paymentMethod(payment.getPaymentMethod().toString())
                .paymentProvider(payment.getPaymentProvider())
                .totalAmount(payment.getTotalAmount().getAmount())
                .transactionId(payment.getTransactionId())
                .paymentStatus(payment.getPaymentStatus().getDescription())
                .paidAt(payment.getPaidAt())
                .build();
    }
}
