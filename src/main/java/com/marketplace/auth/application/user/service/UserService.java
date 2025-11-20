package com.marketplace.auth.application.user.service;

import com.marketplace.auth.domain.aggregate.UserAggregate;
import com.marketplace.auth.domain.entity.User;

public interface UserService {

    UserAggregate createUser(User user, String userType);

    UserAggregate findByUsername(String username);

    UserAggregate findById(Integer userId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
