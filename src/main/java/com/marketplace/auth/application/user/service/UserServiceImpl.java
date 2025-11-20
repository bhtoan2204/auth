package com.marketplace.auth.application.user.service;

import org.springframework.stereotype.Service;

import com.marketplace.auth.domain.aggregate.UserAggregate;
import com.marketplace.auth.domain.entity.User;
import com.marketplace.auth.infrastructure.persistence.mapper.EntityMapper;
import com.marketplace.auth.infrastructure.persistence.model.CustomerEntity;
import com.marketplace.auth.infrastructure.persistence.model.UserAccountEntity;
import com.marketplace.auth.infrastructure.persistence.repository.FactoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final FactoryRepository factoryRepository;
    private final EntityMapper entityMapper;

    @Override
    public UserAggregate createUser(User user, String userType) {
        UserAccountEntity userEntity = entityMapper.toEntity(user);
        UserAccountEntity savedUser = factoryRepository.getUserAccountRepository().save(userEntity);

        User savedUserDomain = entityMapper.toDomain(savedUser);
        UserAggregate aggregate = UserAggregate.builder()
                .user(savedUserDomain)
                .build();

        if ("customer".equalsIgnoreCase(userType)) {
            CustomerEntity customerEntity = CustomerEntity.builder()
                    .userAccount(savedUser)
                    .build();
            CustomerEntity savedCustomer = factoryRepository.getCustomerRepository().save(customerEntity);
            aggregate.setCustomer(entityMapper.toDomain(savedCustomer));
        }

        return aggregate;
    }

    @Override
    public UserAggregate findByUsername(String username) {
        UserAccountEntity entity = factoryRepository.getUserAccountRepository()
                .findByUsername(username)
                .orElse(null);

        if (entity == null) {
            return null;
        }

        User user = entityMapper.toDomain(entity);
        UserAggregate aggregate = UserAggregate.builder()
                .user(user)
                .build();

        factoryRepository.getCustomerRepository()
                .findByUserId(entity.getId())
                .ifPresent(customerEntity -> {
                    aggregate.setCustomer(entityMapper.toDomain(customerEntity));
                });

        return aggregate;
    }

    @Override
    public UserAggregate findById(Integer userId) {
        UserAccountEntity entity = factoryRepository.getUserAccountRepository()
                .findById(userId)
                .orElse(null);

        if (entity == null) {
            return null;
        }

        User user = entityMapper.toDomain(entity);
        UserAggregate aggregate = UserAggregate.builder()
                .user(user)
                .build();

        factoryRepository.getCustomerRepository()
                .findByUserId(userId)
                .ifPresent(customerEntity -> {
                    aggregate.setCustomer(entityMapper.toDomain(customerEntity));
                });

        return aggregate;
    }

    @Override
    public boolean existsByUsername(String username) {
        return factoryRepository.getUserAccountRepository().existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return factoryRepository.getUserAccountRepository().existsByEmail(email);
    }
}
