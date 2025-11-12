package com.ogirafferes.lxp.sales.domain.model;

import com.ogirafferes.lxp.global.common.model.Money;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PaymentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_item_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(nullable = false)
    private Long courseId;

    @Embedded
    private Money price;

    @Builder
    private PaymentItem(Payment payment, Long courseId, Money price) {
        this.payment = payment;
        this.courseId = courseId;
        this.price = price;
    }

    public static PaymentItem createInitialPaymentItem(Long courseId, Money price) {
        return PaymentItem.builder()
                .courseId(courseId)
                .price(price)
                .build();
    }

    protected void setPaymentId(Payment payment) {
        this.payment = payment;
    }

}