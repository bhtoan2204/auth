package com.marketplace.auth.config.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.marketplace.auth.application.user.service.UserService;
import com.marketplace.auth.domain.aggregate.UserAggregate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Integer userId = Integer.valueOf(username);
            UserAggregate userAggregate = userService.findById(userId);

            if (userAggregate == null) {
                throw new UsernameNotFoundException("User not found with id: " + userId);
            }

            return User.builder()
                    .username(userId.toString())
                    .password("")
                    .disabled(!userAggregate.isActive())
                    .accountExpired(false)
                    .accountLocked(userAggregate.isLocked())
                    .credentialsExpired(false)
                    .authorities("ROLE_USER")
                    .build();

        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid user ID format: " + username);
        }
    }
}
