package com.ogirafferes.lxp.sales.presentation.http;

import com.ogirafferes.lxp.identity.application.adapter.CustomUserPrincipal;
import com.ogirafferes.lxp.sales.application.service.CartService;
import com.ogirafferes.lxp.sales.application.service.PaymentService;
import com.ogirafferes.lxp.sales.presentation.dto.RequestPayment;
import com.ogirafferes.lxp.sales.presentation.dto.ResponseCartInfo;
import com.ogirafferes.lxp.sales.presentation.dto.ResponsePayment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sales/payment")
public class PaymentController {

    private PaymentService paymentService;
    private CartService cartService;

    public PaymentController(PaymentService paymentService, CartService cartService) {
        this.paymentService = paymentService;
        this.cartService = cartService;
    }

    /**
     * [GET] 장바구니 품목 결제 페이지 요청
     * @param model 데이터 전달 모델
     * @param principal 사용자 정보
     * @return 장바구니 품목 결제 요청 페이지
     */
    @GetMapping("/request")
    public String showPaymentRequestPage(Model model, @AuthenticationPrincipal CustomUserPrincipal principal) {
        Long signInUserId = principal.getUserId();
        ResponseCartInfo responseCartInfo = cartService.requestPaymentWithCartItem(signInUserId);

        model.addAttribute("responseCartInfo", responseCartInfo);
        model.addAttribute("cartItems", responseCartInfo.getCartItems());

        return "sales/payment/request";
    }

    /**
     * [POST] 장바구니 품목 기반 결제 요청
     * @param request
     * @return
     */
    @PostMapping("/request")
    public String processPayment(@ModelAttribute RequestPayment request) {
        ResponsePayment result = paymentService.processPayment(request);

        return "redirect:/sales/payment/result/" + result.getOrderNumber();
    }

    @GetMapping("/result/{orderNumber}")
    public String showPaymentResultPage(@PathVariable String orderNumber, Model model) {
        ResponsePayment result = paymentService.getPaymentResult(orderNumber);

        model.addAttribute("paymentResult", result);
        return "sales/payment/result";
    }
}
