package com.ogirafferes.lxp.sales.application.service;

import com.ogirafferes.lxp.sales.application.pg.PaymentGatewayClient;
import com.ogirafferes.lxp.sales.application.pg.PaymentResult;
import com.ogirafferes.lxp.sales.domain.model.Cart;
import com.ogirafferes.lxp.sales.domain.model.CartItem;
import com.ogirafferes.lxp.sales.domain.model.Payment;
import com.ogirafferes.lxp.sales.domain.repository.CartItemRepository;
import com.ogirafferes.lxp.sales.domain.repository.PaymentRepository;
import com.ogirafferes.lxp.sales.presentation.dto.RequestPayment;
import com.ogirafferes.lxp.sales.presentation.dto.ResponsePayment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CartItemRepository cartItemRepository;
    private final PaymentGatewayClient paymentGatewayClient;
    private final CartService cartService;

    public PaymentService(PaymentRepository paymentRepository, CartItemRepository cartItemRepository, PaymentGatewayClient paymentGatewayClient, CartService cartService) {
        this.paymentRepository = paymentRepository;
        this.cartItemRepository = cartItemRepository;
        this.paymentGatewayClient = paymentGatewayClient;
        this.cartService = cartService;
    }

    /**
     * 장바구니 품목 기반 결제
     * @param requestPayment 결제 요청
     * @return 결제 결과
     */
    @Transactional
    public ResponsePayment processPayment(RequestPayment requestPayment) {
        List<CartItem> cartItems = cartItemRepository.findAllByIdIn(requestPayment.getCartItemIds());

        Payment newPayment = Payment.createInitialPayment(
                requestPayment.getUserId(),
                requestPayment.getPaymentMethod(),
                requestPayment.getPaymentProvider()
        );
        newPayment.setItems(Cart.getPaymentItems(cartItems));

        paymentRepository.save(newPayment);

        // PG사 결제(FAKE)
        PaymentResult result = paymentGatewayClient.requestPayment(newPayment.getId(), newPayment.getTotalAmount());

        Payment targetPayment = paymentRepository.findById(newPayment.getId())
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        if (result.isSuccess()) {
            targetPayment.completePayment(result.getTransactionId(), result.getPaidAt());
            requestPayment.getCartItemIds().forEach(cartService::clearItems);
        } else {
            targetPayment.failPayment();
        }

        Payment savedPayment = paymentRepository.save(targetPayment);

        return ResponsePayment.of(savedPayment);
    }

    @Transactional(readOnly = true)
    public ResponsePayment getPaymentResult(String orderNumber) {
        // 1. orderNumber로 결과 조회
        Payment loadPayment = paymentRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        // 2. Response로 가공해서 넘겨주면 끝?
        return ResponsePayment.of(loadPayment);
    }
}
