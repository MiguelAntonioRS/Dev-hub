package com.devhub.opendevplatform.service;

import com.devhub.opendevplatform.model.Follow;
import com.devhub.opendevplatform.model.User;
import java.util.List;

public interface FollowService {
    void follow(User follower, User following);
    void unfollow(User follower, User following);
    boolean isFollowing(User follower, User following);
    List<User> getFollowers(User user);
    List<User> getFollowing(User user);
    long getFollowerCount(User user);
    long getFollowingCount(User user);
}