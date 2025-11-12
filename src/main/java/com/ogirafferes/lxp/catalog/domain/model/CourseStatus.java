package com.ogirafferes.lxp.catalog.domain.model;

public enum CourseStatus {
    DRAFT,        // 작성 중 (미공개)
    PUBLISHED,    // 공개 중
    ARCHIVED, INACTIVE, ACTIVE;     // 보관/과거 강좌
}
