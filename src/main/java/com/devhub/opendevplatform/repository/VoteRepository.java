package com.devhub.opendevplatform.repository;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserAndResource(User user, Resource resource);

    void deleteByResourceId(Long resourceId);

    List<Vote> findByUser(User user);

    @Query("SELECT COALESCE(SUM(v.value), 0) FROM Vote v WHERE v.resource.id = :resourceId")
    int getTotalVotesByResourceId(Long resourceId);
}
