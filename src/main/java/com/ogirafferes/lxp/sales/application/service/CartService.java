package com.ogirafferes.lxp.sales.application.service;

import com.ogirafferes.lxp.sales.domain.model.CartItem;
import com.ogirafferes.lxp.sales.domain.repository.CartItemRepository;
import com.ogirafferes.lxp.sales.presentation.dto.ResponseCartInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * 장바구니 품목 기반 결제 페이지 요청
     * @param userId 로그인 중인 사용자 ID
     * @return 장바구니 정보 DTO
     */
    @Transactional(readOnly = true)
    public ResponseCartInfo requestPaymentWithCartItem(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        List<ResponseCartInfo.ResponseCartItem> responseCartItems =
                cartItems.stream().map(ResponseCartInfo.ResponseCartItem::of).toList();
        List<Long> cartItemIds = cartItems.stream().map(CartItem::getId).toList();

        return ResponseCartInfo.from(userId, cartItemIds, responseCartItems);
    }

    /**
     * 장바구니 비우기
     * @param cartItemId 장바구니 아이템 ID
     */
    public void clearItems(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

}
