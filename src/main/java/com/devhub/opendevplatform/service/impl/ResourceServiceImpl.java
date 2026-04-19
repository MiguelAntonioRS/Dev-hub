package com.devhub.opendevplatform.service.impl;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.Resource.ResourceStatus;
import com.devhub.opendevplatform.repository.ResourceRepository;
import com.devhub.opendevplatform.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Resource addResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public List<Resource> searchResources(String keyword) {
        return resourceRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Resource> listAll() {
        return resourceRepository.findAll();
    }

    @Override public Optional<Resource> findById(Long id) {
        return resourceRepository.findById(id);
    }

    @Override
    public Resource getResourceById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with id " + id));
    }

    @Override
    public List<Resource> findTopVoted() {
        return resourceRepository.findTopVoted();
    }

    @Override
    public List<Resource> findRecent() {
        return resourceRepository.findRecent();
    }

    @Override
    public List<Resource> findByCategory(String category) {
        return resourceRepository.findByCategory(category);
    }

    @Override
    public List<Resource> findByAuthor(String author) {
        return resourceRepository.findByAuthor(author);
    }

    @Override
    public List<Resource> search(String query) {
        return resourceRepository.search(query);
    }

    @Override
    public Page<Resource> findAllPageable(Pageable pageable) {
        return resourceRepository.findAllApproved(pageable);
    }

    @Override
    public Page<Resource> findByStatusPageable(ResourceStatus status, Pageable pageable) {
        return resourceRepository.findByStatusPageable(status, pageable);
    }

    @Override
    public Resource updateStatus(Long id, ResourceStatus status) {
        Resource resource = getResourceById(id);
        resource.setStatus(status);
        return resourceRepository.save(resource);
    }

    @Override
    public long countByStatus(ResourceStatus status) {
        return resourceRepository.countByStatus(status);
    }
}
