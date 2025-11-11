package com.ogirafferes.lxp.community.presentation.http;

import com.ogirafferes.lxp.community.application.CommunityService;
import com.ogirafferes.lxp.community.domain.model.Post;
import com.ogirafferes.lxp.community.presentation.dto.CreatePostRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    private final CommunityService communityService;
    public PostController(CommunityService communityService) {
        this.communityService = communityService;

    }

    @GetMapping("/post")
    public String findAll(Model model) {
        List<Post> posts = communityService.findAll();
        model.addAttribute("posts", posts);
        return "post";
    }

    @GetMapping("/post/new")
    public String writeCreateFrom(Model model) {
        model.addAttribute("post", new CreatePostRequest());
        return "post-form";
    }

    @GetMapping("/post/edit/{id}")
    public String writeEditFrom(@PathVariable Long id, Model model) {
        Post post = communityService.findById(id);
        model.addAttribute("post", post);

        return "post-edit-form";
    }

    @PostMapping("/post")
    public String createPost(@ModelAttribute CreatePostRequest createPostRequest, HttpSession session) {

        User loginUser = (User) session.getAttribute("user");

        communityService.create(loginUser.getUserId(), createPostRequest);
        return "redirect:/post";

    }

    @PostMapping("/post/{id}")
    public String updatePost(@PathVariable Long id, CreatePostRequest createPostRequest, HttpSession session) {

        User loginUser = (User) session.getAttribute("user");

        communityService.update(id,loginUser.getUserId(),createPostRequest);
        return "redirect:/post";
    }

    @PostMapping("/post/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        User loginUser = (User) session.getAttribute("user");

        communityService.delete(id, loginUser.getUserId());
        return "redirect:/post";
    }

}
