package com.ogirafferes.lxp.sales.application.service;

import com.ogirafferes.lxp.catalog.application.CourseCatalogService;
import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.identity.application.UserService;
import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.sales.domain.model.CartItem;
import com.ogirafferes.lxp.sales.domain.repository.CartItemRepository;
import com.ogirafferes.lxp.sales.presentation.dto.ResponseAddedCartItem;
import com.ogirafferes.lxp.sales.presentation.dto.ResponseCartInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CourseCatalogService courseCatalogService;
    private final UserService userService;

    public CartService(
            CartItemRepository cartItemRepository,
            CourseCatalogService courseCatalogService,
            UserService userService) {
        this.cartItemRepository = cartItemRepository;
        this.courseCatalogService = courseCatalogService;
        this.userService = userService;
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
    @Transactional
    public void clearItems(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    /**
     * 장바구니 추가
     * @param userId 로그인 된 유저 ID
     * @param courseId 장바구니에 추가 할 강좌 ID
     * @return 추가된 장바구니 ID
     */
    @Transactional
    public Long addToCart(Long userId, Long courseId) {
        if(cartItemRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new IllegalArgumentException("이미 장바구니에 등록된 강좌입니다.");
        }

        Course course = courseCatalogService.getCourseDetail(courseId);

        CartItem savedCartItem = cartItemRepository.save(CartItem.from(
                userService.getUser(userId),
                courseCatalogService.getCourseDetail(courseId)
        ));

        return savedCartItem.getId();
    }

    /**
     * 장바구니 리스트
     * @param userId 장바구니 조회 대상의 사용자 ID
     * @return 장바구니 리스트
     */
    @Transactional(readOnly = true)
    public List<ResponseAddedCartItem> getCartItemList(Long userId) {
        return cartItemRepository.findAllByUserId(userId).stream()
                .map(cartItem -> ResponseAddedCartItem.from(
                        cartItem.getId(),
                        cartItem.getCourse().getInstructor(),
                        cartItem.getCourse()
                )).toList();
    }

    @Transactional
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
