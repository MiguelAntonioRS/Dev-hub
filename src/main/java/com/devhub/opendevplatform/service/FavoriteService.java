package com.devhub.opendevplatform.service;

import com.devhub.opendevplatform.model.Favorite;
import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.FavoriteRepository;
import com.devhub.opendevplatform.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    public Favorite toggleFavorite(User user, Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElse(null);
        if (resource == null) return null;
        
        Optional<Favorite> existing = favoriteRepository.findByUserAndResource(user, resource);
        
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            return null;
        } else {
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setResource(resource);
            return favoriteRepository.save(favorite);
        }
    }

    public List<Favorite> getUserFavorites(User user) {
        return favoriteRepository.findByUser(user);
    }

    public boolean isFavorite(User user, Resource resource) {
        return favoriteRepository.existsByUserAndResource(user, resource);
    }

    public long getFavoriteCount(Resource resource) {
        return favoriteRepository.countByResource(resource);
    }
}