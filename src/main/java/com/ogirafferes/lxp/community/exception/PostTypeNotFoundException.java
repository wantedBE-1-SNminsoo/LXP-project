package com.ogirafferes.lxp.community.exception;

public class PostTypeNotFoundException extends RuntimeException {
    public PostTypeNotFoundException(Long postTypeId) {
        super("게시글 유형을 찾을 수 없습니다." + postTypeId);
    }
}
