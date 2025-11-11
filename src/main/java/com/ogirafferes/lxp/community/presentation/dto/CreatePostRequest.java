package com.ogirafferes.lxp.community.presentation.dto;

import lombok.Getter;

@Getter
public class CreatePostRequest {
    private String title;
    private String content;
    private Long postTypeId;
}
