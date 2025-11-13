package com.ogirafferes.lxp.community.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("존재하지 않은 사용자 입니다." + userId);
    }
}
