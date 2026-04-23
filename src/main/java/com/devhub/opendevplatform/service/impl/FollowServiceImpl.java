package com.devhub.opendevplatform.service.impl;

import com.devhub.opendevplatform.model.Follow;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.FollowRepository;
import com.devhub.opendevplatform.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Override
    public void follow(User follower, User following) {
        if (!follower.getId().equals(following.getId()) && !isFollowing(follower, following)) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);
            followRepository.save(follow);
        }
    }

    @Override
    public void unfollow(User follower, User following) {
        followRepository.findByFollowerAndFollowing(follower, following)
                .ifPresent(followRepository::delete);
    }

    @Override
    public boolean isFollowing(User follower, User following) {
        return followRepository.existsByFollowerAndFollowing(follower, following);
    }

    @Override
    public List<User> getFollowers(User user) {
        return followRepository.findByFollowing(user).stream()
                .map(Follow::getFollower)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getFollowing(User user) {
        return followRepository.findByFollower(user).stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());
    }

    @Override
    public long getFollowerCount(User user) {
        return followRepository.countByFollowing(user);
    }

    @Override
    public long getFollowingCount(User user) {
        return followRepository.countByFollower(user);
    }
}