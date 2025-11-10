package com.ogirafferes.lxp.learning.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lecture_progresses",
        uniqueConstraints = @UniqueConstraint(columnNames = {"enrollment_id", "lecture_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private com.ogirafferes.lxp.catalog.domain.model.Lecture lecture;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;
}
