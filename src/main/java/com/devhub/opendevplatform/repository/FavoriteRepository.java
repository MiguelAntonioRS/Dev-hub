package com.devhub.opendevplatform.repository;

import com.devhub.opendevplatform.model.Favorite;
import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    Optional<Favorite> findByUserAndResource(User user, Resource resource);
    boolean existsByUserAndResource(User user, Resource resource);
    void deleteByUserAndResource(User user, Resource resource);
    long countByResource(Resource resource);
}