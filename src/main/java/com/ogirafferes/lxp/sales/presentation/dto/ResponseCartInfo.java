package com.ogirafferes.lxp.sales.presentation.dto;

import com.ogirafferes.lxp.sales.domain.model.CartItem;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class ResponseCartInfo {

    private Long userId;
    private BigDecimal totalAmount;
    private List<Long> cartItemIds;

    private List<ResponseCartItem> cartItems;

    @Builder
    private ResponseCartInfo(Long userId, List<Long> cartItemIds, List<ResponseCartItem> cartItems) {
        this.userId = userId;
        this.cartItems = cartItems;
        this.cartItemIds = cartItemIds;

        this.totalAmount = calculateTotalAmount();
    }

    @Getter
    public static class ResponseCartItem {
        private String productName;
        private BigDecimal price;

        @Builder
        public ResponseCartItem(String productName, BigDecimal price) {
            this.productName = productName;
            this.price = price;
        }

        public static ResponseCartItem of(CartItem cartItem) {
            return ResponseCartItem.builder()
                    .productName(cartItem.getCourseId().toString())
                    .price(BigDecimal.valueOf(cartItem.getCourseId()))
                    .build();
        }
    }

    private BigDecimal calculateTotalAmount() {
        return cartItems.stream()
                .map(ResponseCartItem::getPrice)
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new ArithmeticException("price is null"));
    }
}
