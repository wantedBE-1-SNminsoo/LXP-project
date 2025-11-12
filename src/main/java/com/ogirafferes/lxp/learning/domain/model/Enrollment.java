package com.ogirafferes.lxp.learning.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 프로텍티드 기본생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE)    // 외부 직접 호출 제한
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private com.ogirafferes.lxp.identity.domain.model.User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private com.ogirafferes.lxp.catalog.domain.model.Course course;

    @OneToOne
    @JoinColumn(name = "payment_item_id", unique = true)
    private com.ogirafferes.lxp.sales.domain.model.PaymentItem paymentItem;

    @Column(name = "enrollment_status", nullable = false)
    private String enrollmentStatus;

    @Column(name = "progress_percentage", nullable = false)
    private int progressPercentage = 0;

    @Column(name = "enrolled_at", nullable = false)
    private LocalDateTime enrolledAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}