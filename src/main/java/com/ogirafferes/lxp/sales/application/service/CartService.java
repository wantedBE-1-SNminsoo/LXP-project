package com.ogirafferes.lxp.sales.application.service;

import com.ogirafferes.lxp.sales.domain.repository.CartItemRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * 장바구니 비우기
     * @param cartItemId 장바구니 아이템 ID
     */
    public void clearItems(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

}
