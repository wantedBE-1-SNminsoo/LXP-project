package com.ogirafferes.lxp.catalog.domain.model;

import com.ogirafferes.lxp.identity.domain.model.User;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_status", nullable = false)
    private CourseStatus courseStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("lectureOrder ASC")
    @Builder.Default
    private List<Lecture> lectures = new ArrayList<>();

    // 연관관계 편의 메서드 (Aggregate 내부 관리)
    public void addLecture(Lecture lecture) {
        lectures.add(lecture);
        lecture.setCourse(this);
    }

    public void removeLecture(Lecture lecture) {
        lectures.remove(lecture);
        lecture.setCourse(null);
    }

    // 간단한 상태 변경 메서드만 엔티티에 유지
    public void updateBasicInfo(String title, String description, BigDecimal price) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (description != null) {
            this.description = description;
        }
        if (price != null && price.compareTo(BigDecimal.ZERO) >= 0) {
            this.price = price;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void changeStatus(CourseStatus newStatus) {
        this.courseStatus = newStatus;
        this.updatedAt = LocalDateTime.now();
    }
}
