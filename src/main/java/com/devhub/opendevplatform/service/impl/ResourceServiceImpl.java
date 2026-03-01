package com.devhub.opendevplatform.service.impl;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.repository.ResourceRepository;
import com.devhub.opendevplatform.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
