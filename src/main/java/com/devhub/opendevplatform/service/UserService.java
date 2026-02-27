package com.devhub.opendevplatform.service;

import com.devhub.opendevplatform.model.User;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public interface UserService {

    void registerUser(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
