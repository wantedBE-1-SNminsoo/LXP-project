package com.ogirafferes.lxp.community.presentation.http;

import com.ogirafferes.lxp.community.application.CommunityService;
import com.ogirafferes.lxp.community.domain.model.Post;
import com.ogirafferes.lxp.community.presentation.dto.CreatePostRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class PostController {

    private final CommunityService communityService;
    public PostController(CommunityService communityService) {
        this.communityService = communityService;

    }

    @GetMapping("/posts")
    public String findAll(Model model) {
        List<Post> posts = communityService.findAll();
        model.addAttribute("posts", posts);
        return "post";
    }

    @GetMapping("/posts/new")
    public String writeCreateFrom(Model model) {
        model.addAttribute("post", new CreatePostRequest());
        return "post-from";
    }


}
