package com.ogirafferes.lxp.global.common.model;

import com.ogirafferes.lxp.sales.domain.model.PaymentItem;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Embeddable
public final class Money {

    private final BigDecimal amount;

    private Money(BigDecimal amount) {
        if(amount == null)
            throw new NullPointerException("amount cannot be null");

        this.amount = amount.setScale(0, RoundingMode.HALF_UP);
    }

    protected Money() {
        this.amount = BigDecimal.ZERO;
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public BigDecimal getAmount() {
        return amount.add(BigDecimal.ZERO);
    }

    public Money plus(Money other) {
        return new Money(this.amount.add(other.getAmount()));
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount.intValue(), money.amount.intValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.amount.intValue());
    }
}
