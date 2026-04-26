package com.devhub.opendevplatform.service;

import com.devhub.opendevplatform.model.Rating;
import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public void rateResource(User user, Resource resource, int stars) {
        Rating rating = ratingRepository.findByUserAndResource(user, resource)
                .orElse(new Rating());
        rating.setUser(user);
        rating.setResource(resource);
        rating.setStars(stars);
        ratingRepository.save(rating);
    }

public double getAverageRating(Resource resource) {
        List<Rating> ratings = ratingRepository.findByResource(resource);
        if (ratings.isEmpty()) return 0;
        return ratings.stream().mapToInt(Rating::getStars).average().orElse(0);
    }

    public int getUserRating(User user, Resource resource) {
        return ratingRepository.findByUserAndResource(user, resource)
                .map(Rating::getStars)
                .orElse(0);
    }
}