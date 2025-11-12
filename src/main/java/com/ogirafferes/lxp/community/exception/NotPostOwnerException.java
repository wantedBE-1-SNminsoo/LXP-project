package com.ogirafferes.lxp.community.exception;

public class NotPostOwnerException extends RuntimeException {
    public NotPostOwnerException() {
        super("본인 글만 수정/삭제 가능합니다.");
    }
}
