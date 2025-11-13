package com.ogirafferes.lxp.sales.application.pg;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PaymentResult {

    private PayCode code;
    private String transactionId;
    private LocalDateTime paidAt;

    @Builder
    private PaymentResult(PayCode code, String transactionId, LocalDateTime paidAt) {
        this.code = code;
        this.transactionId = transactionId;
        this.paidAt = paidAt;
    }

    public static PaymentResult createFakeSuccess() {
        return PaymentResult.builder()
                .code(PayCode.SUCCESS)
                .transactionId(UUID.randomUUID().toString())
                .paidAt(LocalDateTime.now())
                .build();
    }

    public static PaymentResult createFakeFailure() {
        return PaymentResult.builder()
                .code(PayCode.FAIL)
                .transactionId(UUID.randomUUID().toString())
                .paidAt(LocalDateTime.now())
                .build();
    }

    public boolean isSuccess() {
        return this.code.equals(PayCode.SUCCESS);
    }
}
