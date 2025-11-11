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
  /// 외래키 ID 칼럼을 필드로 관리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "payment_item_id", unique = true)
    private Long paymentItemId;

    @Column(name = "enrollment_status", nullable = false)
    private String enrollmentStatus;

    @Column(name = "progress_percentage", nullable = false)
    private int progressPercentage = 0;

    @Column(name = "enrolled_at", nullable = false)
    private LocalDateTime enrolledAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    // 필요 시 생성자, 상태 변경 메서드 등 도메인 행위 추가 가능
}
