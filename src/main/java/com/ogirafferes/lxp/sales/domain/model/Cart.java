package com.ogirafferes.lxp.sales.domain.model;

import com.ogirafferes.lxp.global.common.model.Money;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {

    /**
     * 장바구니 품목 -> 결제 품목 변경
     * @param cartItems 장바구니 품목 리스트
     * @return 결제 품목 리스트
     */
    public static List<PaymentItem> getPaymentItems(List<CartItem> cartItems) {
        return cartItems.stream().map((item) ->
            PaymentItem.createInitialPaymentItem(
                item.getCourse().getId(),
                Money.of(BigDecimal.valueOf(20000)))
        ).collect(Collectors.toList());
    }

}

