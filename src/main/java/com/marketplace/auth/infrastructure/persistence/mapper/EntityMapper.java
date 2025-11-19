package com.marketplace.auth.infrastructure.persistence.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.marketplace.auth.domain.entity.Customer;
import com.marketplace.auth.domain.entity.Employee;
import com.marketplace.auth.domain.entity.Permission;
import com.marketplace.auth.domain.entity.Role;
import com.marketplace.auth.domain.entity.User;
import com.marketplace.auth.infrastructure.persistence.model.CustomerEntity;
import com.marketplace.auth.infrastructure.persistence.model.EmployeeEntity;
import com.marketplace.auth.infrastructure.persistence.model.PermissionEntity;
import com.marketplace.auth.infrastructure.persistence.model.RoleEntity;
import com.marketplace.auth.infrastructure.persistence.model.UserAccountEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EntityMapper {

    private final UserMapper userMapper;
    private final CustomerMapper customerMapper;
    private final EmployeeMapper employeeMapper;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    public User toDomain(UserAccountEntity entity) {
        return userMapper.toDomain(entity);
    }

    public UserAccountEntity toEntity(User domain) {
        return userMapper.toEntity(domain);
    }

    public Customer toDomain(CustomerEntity entity) {
        return customerMapper.toDomain(entity);
    }

    public CustomerEntity toEntity(Customer domain) {
        return customerMapper.toEntity(domain);
    }

    public Employee toDomain(EmployeeEntity entity) {
        return employeeMapper.toDomain(entity);
    }

    public EmployeeEntity toEntity(Employee domain) {
        return employeeMapper.toEntity(domain);
    }

    public Role toDomain(RoleEntity entity) {
        return roleMapper.toDomain(entity);
    }

    public RoleEntity toEntity(Role domain) {
        return roleMapper.toEntity(domain);
    }

    public Permission toDomain(PermissionEntity entity) {
        return permissionMapper.toDomain(entity);
    }

    public PermissionEntity toEntity(Permission domain) {
        return permissionMapper.toEntity(domain);
    }

    public List<User> toDomainList(List<UserAccountEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public List<Role> toDomainRoleList(List<RoleEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public List<Permission> toDomainPermissionList(List<PermissionEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}

