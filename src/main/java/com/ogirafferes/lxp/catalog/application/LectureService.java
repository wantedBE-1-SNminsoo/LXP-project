package com.ogirafferes.lxp.catalog.application;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.catalog.domain.model.Lecture;
import com.ogirafferes.lxp.catalog.domain.repository.CourseRepository;
import com.ogirafferes.lxp.catalog.domain.repository.LectureRepository;
import com.ogirafferes.lxp.catalog.domain.service.LectureDomainService;
import com.ogirafferes.lxp.catalog.presentation.dto.LectureCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {

    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;
    private final LectureDomainService lectureDomainService;

    // 강좌별 강의 목록 조회
    public List<Lecture> getLecturesByCourse(Long courseId) {
        return lectureRepository.findByCourseIdOrderByLectureOrderAsc(courseId);
    }

    // 강의 상세 조회
    public Lecture getLectureDetail(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));
    }

    // 강의 추가
    @Transactional
    public Lecture addLecture(Long courseId, LectureCreateRequest request) {
        Course course = courseRepository.findByIdWithLectures(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌입니다."));

        // 도메인 서비스를 통한 비즈니스 규칙 검증
        lectureDomainService.validateLectureOrder(course, request.getLectureOrder());

        Lecture lecture = Lecture.builder()
                .title(request.getTitle())
                .contentUrl(request.getContentUrl())
                .lectureOrder(request.getLectureOrder())
                .build();

        course.addLecture(lecture);
        return lectureRepository.save(lecture);
    }

    // 강의 수정
    @Transactional
    public Lecture updateLecture(Long lectureId, String title, String contentUrl) {
        Lecture lecture = getLectureDetail(lectureId);
        lecture.changeTitle(title);
        // contentUrl 변경은 별도 메서드가 필요하면 Lecture 엔티티에 추가
        return lecture;
    }

    // 강의 삭제
    @Transactional
    public void deleteLecture(Long courseId, Long lectureId) {
        Course course = courseRepository.findByIdWithLectures(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌입니다."));

        Lecture lecture = getLectureDetail(lectureId);
        course.removeLecture(lecture);
        lectureRepository.delete(lecture);
    }
}
