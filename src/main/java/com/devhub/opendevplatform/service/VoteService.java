package com.devhub.opendevplatform.service;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.model.Vote;
import org.springframework.stereotype.Service;

@Service
public interface VoteService {

    Vote voteResource(User user, Resource resource, int value);
}
