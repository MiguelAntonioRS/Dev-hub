package com.devhub.opendevplatform.repository;

import com.devhub.opendevplatform.model.Follow;
import com.devhub.opendevplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower(User user);
    List<Follow> findByFollowing(User user);
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    boolean existsByFollowerAndFollowing(User follower, User following);
    long countByFollowing(User user);
    long countByFollower(User user);
}