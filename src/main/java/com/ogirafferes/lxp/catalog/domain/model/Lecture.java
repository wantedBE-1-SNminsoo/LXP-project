package com.ogirafferes.lxp.catalog.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "lectures")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 프로텍티드 기본생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE)    // 외부 직접 호출 제한
@Builder
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(name = "content_url", nullable = false)
    private String contentUrl;

    @Column(name = "lecture_order", nullable = false)
    private int lectureOrder;

    // 도메인 행위 예시: 강의 제목 변경 시 setter 대신 메서드로 변경 유도
    public void changeTitle(String newTitle) {
        if (Objects.nonNull(newTitle) && !newTitle.isBlank()) {
            this.title = newTitle;
        }
    }

    // 연관관계 편의 메서드 (양방향 시)
    public void setCourse(Course course) {
        this.course = course;
    }
}
