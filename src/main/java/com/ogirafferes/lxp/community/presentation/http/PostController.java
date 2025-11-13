package com.ogirafferes.lxp.community.presentation.http;

import com.ogirafferes.lxp.community.application.CommunityService;
import com.ogirafferes.lxp.community.domain.model.Post;
import com.ogirafferes.lxp.community.domain.repository.PostTypeRepository;
import com.ogirafferes.lxp.community.presentation.dto.CreatePostRequest;
import com.ogirafferes.lxp.identity.application.adapter.CustomUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/community/post")
public class PostController {

    private final CommunityService communityService;
    private final PostTypeRepository postTypeRepository;

    public PostController(CommunityService communityService, PostTypeRepository postTypeRepository) {
        this.communityService = communityService;
        this.postTypeRepository = postTypeRepository;
    }

    @GetMapping
    public String findAll(Model model) {
        List<Post> posts = communityService.findAll();
        model.addAttribute("posts", posts);
        return "community/post";
    }

    @GetMapping("/new")
    public String writeCreateForm(Model model) {
        model.addAttribute("post", new CreatePostRequest());
        model.addAttribute("postTypes",postTypeRepository.findAll());
        return "community/post-form";
    }

    @GetMapping("/edit/{id}")
    public String writeEditForm(@PathVariable Long id, Model model,
                                @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal) {

        Long userId = customUserPrincipal.getUserId();
        Post post = communityService.findById(id);

        if(!post.getAuthor().getId().equals(userId)){
            return "redirect:/community/post";
        }

        CreatePostRequest form = new CreatePostRequest();
        form.setTitle(post.getTitle());
        form.setContent(post.getContent());
        form.setPostTypeId(post.getPostType().getId());

        model.addAttribute("postId", id);
        model.addAttribute("post", form);
        model.addAttribute("postTypes",postTypeRepository.findAll());

        return "community/post-edit-form";
    }

    @PostMapping
    public String createPost(@Valid @ModelAttribute("post") CreatePostRequest createPostRequest,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal,
                             Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("postTypes", postTypeRepository.findAll());
            return "community/post-form";
        }

        Long userId = customUserPrincipal.getUserId();
        communityService.create(userId, createPostRequest);
        return "redirect:/community/post";

    }

    @PostMapping("/{id}")
    public String updatePost(@PathVariable Long id,
                             @Valid @ModelAttribute("post") CreatePostRequest createPostRequest,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("postTypes", postTypeRepository.findAll());
            model.addAttribute("postId", id);
            return "community/post-edit-form";
        }

        Long userId = customUserPrincipal.getUserId();

        communityService.update(id,userId,createPostRequest);
        return "redirect:/community/post";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal) {

        Long userId = customUserPrincipal.getUserId();

        communityService.delete(id, userId);
        return "redirect:/community/post";
    }

}
