package com.devhub.opendevplatform.repository;

import com.devhub.opendevplatform.model.Rating;
import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserAndResource(User user, Resource resource);
    List<Rating> findByResource(Resource resource);
    List<Rating> findByUser(User user);
}