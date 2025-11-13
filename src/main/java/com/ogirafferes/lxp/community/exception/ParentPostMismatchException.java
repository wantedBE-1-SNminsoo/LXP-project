package com.ogirafferes.lxp.community.exception;

public class ParentPostMismatchException extends RuntimeException {
    public ParentPostMismatchException(Long parentCommentId, Long postId) {
        super("부모 댓글(" + parentCommentId + ")은 게시글(" + postId + ")에 속하지 않습니다.");
    }
}
