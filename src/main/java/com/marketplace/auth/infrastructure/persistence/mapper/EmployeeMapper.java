package com.marketplace.auth.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.marketplace.auth.domain.entity.Employee;
import com.marketplace.auth.infrastructure.persistence.model.EmployeeEntity;

@Component
public class EmployeeMapper {

    public Employee toDomain(EmployeeEntity entity) {
        if (entity == null) {
            return null;
        }

        return Employee.builder()
                .id(entity.getId())
                .userId(entity.getUserAccount() != null ? entity.getUserAccount().getId() : null)
                .employeeCode(entity.getEmployeeCode())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .department(entity.getDepartment())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public EmployeeEntity toEntity(Employee domain) {
        if (domain == null) {
            return null;
        }

        EmployeeEntity entity = EmployeeEntity.builder()
                .employeeCode(domain.getEmployeeCode())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .department(domain.getDepartment())
                .build();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        return entity;
    }
}
