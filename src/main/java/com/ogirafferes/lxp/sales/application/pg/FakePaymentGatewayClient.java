package com.ogirafferes.lxp.sales.application.pg;

import com.ogirafferes.lxp.global.common.model.Money;
import org.springframework.stereotype.Component;

@Component
public class FakePaymentGatewayClient implements PaymentGatewayClient {

    public PaymentResult requestPayment(Long id, Money totalAmount) {
        // 뭔가 결제 함
        return PaymentResult.createFakeSuccess();
    }

}
