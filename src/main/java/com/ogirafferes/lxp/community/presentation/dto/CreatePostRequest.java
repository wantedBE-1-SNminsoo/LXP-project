package com.ogirafferes.lxp.community.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostRequest {
    @NotBlank(message = " 제목은 필수입니다.")
    private String title;
    @NotBlank(message = " 내용은 필수입니다.")
    private String content;
    @NotNull(message = "postTypeId는 필수입니다.")
    private Long postTypeId;
}
