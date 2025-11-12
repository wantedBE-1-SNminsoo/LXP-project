package com.ogirafferes.lxp.sales.presentation.dto;

import com.ogirafferes.lxp.sales.domain.model.CartItem;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 장바구니 품목 응답 DTO
 * - userId         : 로그인 한 사용자 ID
 * - totalAmount    : 총 금액
 * - cartItemIds    : 장바구니에 담긴 품목들의 장바구니 ID
 * - cartItems      : 장바구니 품목 리스트
 */
@Getter
public class ResponseCartInfo {

    private final Long userId;
    private final BigDecimal totalAmount;
    private final List<Long> cartItemIds;

    private final List<ResponseCartItem> cartItems;

    @Builder
    private ResponseCartInfo(Long userId, List<Long> cartItemIds, List<ResponseCartItem> cartItems) {
        this.userId = userId;
        this.cartItems = cartItems;
        this.cartItemIds = cartItemIds;
        this.totalAmount = BigDecimal.ZERO.add(calculateTotalAmount(cartItems));
    }

    public static ResponseCartInfo from(Long userId, List<Long> cartItemIds, List<ResponseCartItem> cartItems) {
        return ResponseCartInfo.builder()
                .userId(userId)
                .cartItems(cartItems)
                .cartItemIds(cartItemIds)
                .build();
    }

    @Getter
    public static class ResponseCartItem {
        private final String productName;
        private final BigDecimal price;

        @Builder
        private ResponseCartItem(String productName, BigDecimal price) {
            this.productName = productName;
            this.price = price;
        }

        public static ResponseCartItem of(CartItem cartItem) {
            return ResponseCartItem.builder()
                    .productName(cartItem.getCourse().getTitle())
                    .price(cartItem.getCourse().getPrice().add(BigDecimal.ZERO))
                    .build();
        }
    }

    private BigDecimal calculateTotalAmount(List<ResponseCartItem> cartItems) {
        return cartItems.stream()
                .map(ResponseCartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
