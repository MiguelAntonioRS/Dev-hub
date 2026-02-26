package com.devhub.opendevplatform.service;

import com.devhub.opendevplatform.model.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ResourceService {

    Resource addResource(Resource resource);

    List<Resource> searchResources(String keyword);

    List<Resource> listAll();
}
