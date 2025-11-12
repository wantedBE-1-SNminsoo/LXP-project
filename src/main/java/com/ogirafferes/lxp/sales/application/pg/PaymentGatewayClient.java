package com.ogirafferes.lxp.sales.application.pg;

import com.ogirafferes.lxp.global.common.model.Money;

public interface PaymentGatewayClient {

    PaymentResult requestPayment(Long id, Money totalAmount);

}
