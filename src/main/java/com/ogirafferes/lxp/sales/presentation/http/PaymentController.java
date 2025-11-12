package com.ogirafferes.lxp.sales.presentation.http;

import com.ogirafferes.lxp.sales.application.service.PaymentService;
import com.ogirafferes.lxp.sales.presentation.dto.RequestPayment;
import com.ogirafferes.lxp.sales.presentation.dto.ResponseCartInfo;
import com.ogirafferes.lxp.sales.presentation.dto.ResponsePayment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sales/payment")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/request")
    public String showPaymentRequestPage(Model model) {
        ResponseCartInfo paymentRequest = paymentService.buildPaymentRequest(1L);

        model.addAttribute("paymentRequest", paymentRequest);
        model.addAttribute("cartItems", paymentRequest.getCartItems()); // 반복문용 목록

        return "sales/payment/request";
    }

    @PostMapping("/request")
    public String processPayment(@ModelAttribute RequestPayment request) {
        ResponsePayment result = paymentService.processPayment(request);

        return "redirect:/sales/payment/result/" + result.getOrderNumber();
    }

    @GetMapping("/result/{orderNumber}")
    public String showPaymentResultPage(@PathVariable String orderNumber, Model model) {
//        ResponsePayment result = paymentService.getPaymentResult(orderNumber);

//        model.addAttribute("paymentResult", result);
        return "sales/payment/result";
    }
}
