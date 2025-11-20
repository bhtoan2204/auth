package com.marketplace.auth.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.marketplace.auth.domain.entity.Customer;
import com.marketplace.auth.infrastructure.persistence.model.CustomerEntity;

@Component
public class CustomerMapper {

    public Customer toDomain(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }

        return Customer.builder()
                .id(entity.getId())
                .userId(entity.getUserAccount() != null ? entity.getUserAccount().getId() : null)
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phoneNumber(entity.getPhoneNumber())
                .dateOfBirth(entity.getDateOfBirth())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public CustomerEntity toEntity(Customer domain) {
        if (domain == null) {
            return null;
        }

        CustomerEntity entity = CustomerEntity.builder()
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .phoneNumber(domain.getPhoneNumber())
                .dateOfBirth(domain.getDateOfBirth())
                .build();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        return entity;
    }
}
