package com.ogirafferes.lxp.community.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCommentRequest {
    @NotNull(message = "postId는 필수입니다.")
    private Long postId;

    private Long parentCommentId;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;




}
