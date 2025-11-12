package com.ogirafferes.lxp.sales.domain.model;

import com.ogirafferes.lxp.global.common.model.Money;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @Embedded
    @Column(nullable = false)
    private Money totalAmount;

    private PaymentMethod paymentMethod;
    private String paymentProvider;

    private String transactionId;

    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PaymentItem> paymentItems;

    @Builder
    private Payment(Long userId, Money totalAmount, PaymentMethod paymentMethod, String paymentProvider, List<PaymentItem> paymentItems) {
        this.userId = userId;
        this.orderNumber = createOrderNumber();
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentProvider = paymentProvider;
        this.paymentItems = paymentItems;
        this.paymentStatus = PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public static Payment createInitialPayment(Long userId, PaymentMethod paymentMethod, String paymentProvider) {
        return Payment.builder()
            .userId(userId)
            .paymentMethod(paymentMethod)
            .paymentProvider(paymentProvider)
            .build();
    }

    /**
     * 주문 번호 생성
     * @return 생성 된 주문 번호
     */
    private String createOrderNumber() {
        // 1. 현재 날짜/시간 포맷 (12자리)
        LocalDateTime now = LocalDateTime.now();
        // DateTimeFormatter.ofPattern("yyMMddHHmmss") : 251111122003 형태
        String datePart = now.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));

        // 2. 0부터 9999 사이의 4자리 랜덤 숫자 생성
        Random random = new Random();
        int randomValue = random.nextInt(10000);

        // 3. 4자리가 되도록 앞에 0을 채움 (예: 5 -> 0005)
        String randomPart = String.format("%04d", randomValue);

        // 4. 두 부분을 결합하여 주문 번호 완성
        String orderNumber = datePart + randomPart;

        return orderNumber;
    }

    /**
     * 결제 품목 데이터 셋팅
     * @param paymentItems 결제 데이터 리스트
     */
    public void setItems(List<PaymentItem> paymentItems) {
        this.paymentItems = paymentItems;
        this.paymentItems.forEach(item -> item.setPaymentId(this));
        calculateTotalAmount();
    }

    /**
     * 아이템들의 모든 값 계산
     */
    private Money calculateTotalAmount() {

        paymentItems.forEach(item -> {

        });
        return paymentItems.stream()
            .map(PaymentItem::getPrice)
            .map(Money::getAmount)
            .reduce((BigDecimal::add))
                .map(Money::of)
            .orElseThrow(() -> new ArithmeticException("Cannot calculate the total amount."));
    }

    /**
     * 결제 성공 처리
     */
    public void completePayment(String transactionId, LocalDateTime paidAt) {
        if(paymentStatus != PaymentStatus.PENDING) {
            throw new  IllegalStateException("이미 처리된 결제입니다.");
        }

        this.paymentStatus = PaymentStatus.COMPLETED;
        this.transactionId = transactionId;
        this.paidAt = paidAt;
    }

    /**
     * 결제 실패 상태로 변경
     */
    public void failPayment() {
        this.paymentStatus = PaymentStatus.FAILED;
    }
}
