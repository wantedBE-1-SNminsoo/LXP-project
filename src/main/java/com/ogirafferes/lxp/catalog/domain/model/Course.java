package com.ogirafferes.lxp.catalog.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 강좌 정보를 저장하는 엔티티
 */
@Entity                                 // JPA 테이블과 매핑
@Table(name = "course")                 // 실제 DB의 테이블명 지정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id                                  // 기본키(Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(name = "course_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
