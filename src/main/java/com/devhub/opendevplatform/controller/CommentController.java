package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.Comment;
import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.ResourceRepository;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String addComment(@RequestParam Long resourceId,
                              @RequestParam String content,
                              Authentication authentication) {
        if (authentication == null) {
            return "redirect:/users/login";
        }
        Comment comment = new Comment();
        comment.setContent(content);
        
        resourceRepository.findById(resourceId).ifPresent(comment::setResource);
        userRepository.findByUsername(authentication.getName()).ifPresent(comment::setUser);
        
        commentService.addComment(comment);
        return "redirect:/resources/" + resourceId;
    }

    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id, @RequestParam Long resourceId) {
        commentService.deleteComment(id);
        return "redirect:/resources/" + resourceId;
    }
}