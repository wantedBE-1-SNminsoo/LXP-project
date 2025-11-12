package com.ogirafferes.lxp.sales.domain.repository;

import com.ogirafferes.lxp.sales.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
