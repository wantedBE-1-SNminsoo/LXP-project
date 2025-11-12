package com.ogirafferes.lxp.learning.domain.model;

import com.ogirafferes.lxp.catalog.domain.model.Lecture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enrollments",
    uniqueConstraints = {
           @UniqueConstraint(columnNames = {"user_id", "course_id"})
       })
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

    @Enumerated(EnumType.STRING)
    @Column(name = "enrollment_status", nullable = false, length = 30)
    private EnrollmentStatus enrollmentStatus;

    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LectureProgress> progresses = new ArrayList<>();

    @Column(name = "progress_percentage", nullable = false)
    private int progressPercentage = 0;

    @Column(name = "enrolled_at", nullable = false)
    private LocalDateTime enrolledAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public void completeLecture(Lecture lecture) {
        boolean alreadyCompleted = progresses.stream()
                .anyMatch(progress -> progress.getLecture().getId().equals(lecture.getId()));

        if (alreadyCompleted) {
            throw new IllegalStateException("Lecture already completed");
        }

        boolean isValidLecture = course.getLectures().stream()
                .anyMatch(lec -> lec.getId().equals(lecture.getId()));

        if (!isValidLecture) {
            throw new IllegalArgumentException("Lecture does not belong to the enrolled course");
        }

        LectureProgress progress = LectureProgress.builder()
                .enrollment(this)
                .lecture(lecture)
                .completedAt(LocalDateTime.now())
                .build();

        progresses.add(progress);
        updateProgressPercentage();
    }

    private void updateProgressPercentage() {
        int totalLectures = course.getLectures().size();
        int completedLectures = progresses.size();
        this.progressPercentage = (int) ((completedLectures / (double) totalLectures) * 100);

        if (progressPercentage == 100) {
            changeComplete();
        }
    }

    private void changeComplete() {
        this.enrollmentStatus = EnrollmentStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public boolean isComplete() {
        return enrollmentStatus == EnrollmentStatus.COMPLETED;
    }

    public int getCompletedLectureCount() {
        return progresses.size();
    }

    public int getTotalLectureCount() {
        return course.getLectures().size();
    }
}