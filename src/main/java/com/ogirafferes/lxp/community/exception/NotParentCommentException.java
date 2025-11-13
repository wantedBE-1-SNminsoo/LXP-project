package com.ogirafferes.lxp.community.exception;

public class NotParentCommentException extends RuntimeException {
    public NotParentCommentException(Long parentCommentId) {
        super("부모 댓글(id = " + parentCommentId + ")이 존재하지않습니다.");
    }
}
