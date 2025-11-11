package com.ogirafferes.lxp.catalog.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자는 외부 접근 제한
@AllArgsConstructor(access = AccessLevel.PRIVATE)   // AllArgsConstructor는 private 처리하여 빌더 등 사용 권장
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING) // ENUM을 String으로 저장, 안전
    @Column(name = "course_status", nullable = false)
    private CourseStatus courseStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("lectureOrder ASC")  // 강의 순서 정렬
    private List<Lecture> lectures;
}
