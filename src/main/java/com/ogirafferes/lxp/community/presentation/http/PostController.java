package com.ogirafferes.lxp.community.presentation.http;

import com.ogirafferes.lxp.community.application.CommentService;
import com.ogirafferes.lxp.community.application.CommunityService;
import com.ogirafferes.lxp.community.domain.model.Post;
import com.ogirafferes.lxp.community.domain.model.PostComment;
import com.ogirafferes.lxp.community.domain.repository.PostTypeRepository;
import com.ogirafferes.lxp.community.presentation.dto.CreatePostRequest;
import com.ogirafferes.lxp.identity.application.adapter.CustomUserPrincipal;
import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.identity.domain.repository.UserRepository;
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
    private final CommentService commentService;
    private final UserRepository userRepository;

    public PostController(CommunityService communityService, PostTypeRepository postTypeRepository, CommentService commentService, UserRepository userRepository) {
        this.communityService = communityService;
        this.postTypeRepository = postTypeRepository;
        this.commentService = commentService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String findAll(Model model) {
        List<Post> posts = communityService.findAll();
        model.addAttribute("posts", posts);
        return "community/post";
    }

    @GetMapping("/my-posts")
    public String findMyPosts(Model model, @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal) {
        Long userId = customUserPrincipal.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")); // Consider a more specific exception
        List<Post> myPosts = communityService.findPostsByAuthor(user);
        model.addAttribute("myPosts", myPosts);
        return "my_page";
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

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model,
                          @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal) {
        Post post = communityService.findById(id);
        Long loginUserId = customUserPrincipal != null ? customUserPrincipal.getUserId() : null;

        List<PostComment> comments = commentService.getCommentsByPostId(id);

        model.addAttribute("post", post);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("comments", comments);

        return  "community/post-detail";
    }

}
