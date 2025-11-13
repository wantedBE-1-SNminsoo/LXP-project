package com.ogirafferes.lxp.community.presentation.http;

import com.ogirafferes.lxp.community.application.CommentService;
import com.ogirafferes.lxp.community.domain.repository.PostCommentRepository;
import com.ogirafferes.lxp.community.presentation.dto.CreateCommentRequest;
import com.ogirafferes.lxp.identity.application.adapter.CustomUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public String createComment(@ModelAttribute CreateCommentRequest createCommentRequest,
                                @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal) {
        Long userId = customUserPrincipal.getUserId();

        commentService.createComment(createCommentRequest, userId);

        return "redirect:/community/post/" + createCommentRequest.getPostId();
    }
}
