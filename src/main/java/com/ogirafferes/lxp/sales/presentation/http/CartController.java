package com.ogirafferes.lxp.sales.presentation.http;

import com.ogirafferes.lxp.identity.application.adapter.CustomUserPrincipal;
import com.ogirafferes.lxp.sales.application.service.CartService;
import com.ogirafferes.lxp.sales.presentation.dto.ResponseAddedCartItem;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * [POST] 장바구니 추가 요청
     * @param courseId
     * @return 장바구니 추가 결과
     */
    @PostMapping("/add/{courseId}")
    public String addToCart(@PathVariable("courseId") Long courseId, @AuthenticationPrincipal CustomUserPrincipal principal) {
        Long signInUserId = principal.getUserId();
        Long savedCartItemId = cartService.addToCart(signInUserId, courseId);

        return "redirect:/cart/list";
    }

    /**
     * [GET] 장바구니 목록 요청
     * @param model 데이터 전달 모델
     * @param principal 사용자 정보
     * @return 장바구니 목록
     */
    @GetMapping("/list")
    public String showCartItemList(Model model, @AuthenticationPrincipal CustomUserPrincipal principal) {
        Long signInUserId = principal.getUserId();
        List<ResponseAddedCartItem> responseAddedCartItems = cartService.getCartItemList(signInUserId);

        model.addAttribute("cartItemList", responseAddedCartItems);

        return "/cart/list";
    }

    /**
     * [POST] 장바구니 삭제 요청
     * @param cartItemId 삭제할 장바구니 ID
     * @return 삭제된 장바구니 리스트 화면
     */
    @PostMapping("/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable Long cartItemId) {
        cartService.deleteCartItem(cartItemId);

        return "redirect:/cart/list";
    }
}