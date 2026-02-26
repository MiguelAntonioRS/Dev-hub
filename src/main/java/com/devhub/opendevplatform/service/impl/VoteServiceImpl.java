package com.devhub.opendevplatform.service.impl;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.model.Vote;
import com.devhub.opendevplatform.repository.VoteRepository;
import com.devhub.opendevplatform.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Vote voteResource(User user, Resource resource, int value) {

        Optional<Vote> existingVote = voteRepository.findByUserAndResource(user, resource);
        if (existingVote.isPresent()) {
            Vote vote = existingVote.get();
            vote.setValue(value);
            return voteRepository.save(vote);
        } else {
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setResource(resource);
            vote.setValue(value);
            return voteRepository.save(vote);
        }
    }
}
