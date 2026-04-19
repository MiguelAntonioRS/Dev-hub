package com.devhub.opendevplatform.service;

import com.devhub.opendevplatform.model.Follow;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public Follow toggleFollow(User follower, User following) {
        if (follower.getId().equals(following.getId())) {
            return null;
        }
        Optional<Follow> existing = followRepository.findByFollowerAndFollowing(follower, following);
        
        if (existing.isPresent()) {
            followRepository.delete(existing.get());
            return null;
        } else {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);
            return followRepository.save(follow);
        }
    }

    public List<Follow> getFollowers(User user) {
        return followRepository.findByFollowing(user);
    }

    public List<Follow> getFollowing(User user) {
        return followRepository.findByFollower(user);
    }

    public boolean isFollowing(User follower, User following) {
        return followRepository.existsByFollowerAndFollowing(follower, following);
    }

    public long getFollowersCount(User user) {
        return followRepository.countByFollowing(user);
    }

    public long getFollowingCount(User user) {
        return followRepository.countByFollower(user);
    }
}