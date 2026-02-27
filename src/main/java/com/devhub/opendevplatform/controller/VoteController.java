package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.config.CustomUserDetails;
import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.service.ResourceService;
import com.devhub.opendevplatform.service.UserService;
import com.devhub.opendevplatform.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/{resourceId}/upvote")
    public String upvote(@PathVariable Long resourceId,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Resource resource = resourceService.findById(resourceId).orElseThrow();
        voteService.voteResource(user, resource, 1);
        return "redirect:/resources";
    }

    @PostMapping("/{resourceId}/downvote")
    public String downvote(@PathVariable Long resourceId,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Resource resource = resourceService.findById(resourceId).orElseThrow();
        voteService.voteResource(user, resource, -1);
        return "redirect:/resources";
    }

}

