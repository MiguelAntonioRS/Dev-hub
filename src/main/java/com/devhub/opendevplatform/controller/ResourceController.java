package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.repository.ResourceRepository;
import com.devhub.opendevplatform.repository.VoteRepository;
import com.devhub.opendevplatform.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private VoteRepository voteRepository;

    @GetMapping
    public String listResources(Model model) {
        model.addAttribute("resources", resourceService.listAll());
        return "resources";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("resource", new Resource());
        return "addResource";
    }

    @PostMapping("/add")
    public String addResource(@ModelAttribute Resource resource) {
        resourceService.addResource(resource);
        return "redirect:/resources";
    }

    @GetMapping("/{id}")
    public String getResourceDetail(@PathVariable Long id, Model model) {
        Resource resource = resourceService.getResourceById(id);
        model.addAttribute("resource", resource);
        return "resourceDetail";
    }

    @GetMapping("/resources")
    public String listResources(@RequestParam(required = false) String category, Model model) {

        List<Resource> resources;
        if (category != null && !category.isEmpty()) {
            resources = resourceRepository.findByCategory(category);
        } else {
            resources = resourceRepository.findAll();
        }
        model.addAttribute("resources", resources);
        return "resources";
    }

    @PostMapping("/delete/{id}")
    public String deleteResource(@PathVariable Long id) {

        voteRepository.deleteByResourceId(id);

        resourceRepository.deleteById(id);
        return "redirect:/resources";
    }

}
