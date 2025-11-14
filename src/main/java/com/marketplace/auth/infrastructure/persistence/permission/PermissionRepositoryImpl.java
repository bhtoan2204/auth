package com.marketplace.auth.infrastructure.persistence.permission;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.marketplace.auth.domain.permission.Permission;
import com.marketplace.auth.domain.permission.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {
    private final PermissionJpaRepository jpaRepository;

    @Override
    public Permission save(Permission permission) {
        PermissionJpaEntity saved = jpaRepository.save(PermissionMapper.toEntity(permission));
        return PermissionMapper.toDomain(saved);
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return jpaRepository.findById(Math.toIntExact(id)).map(PermissionMapper::toDomain);
    }

    @Override
    public List<Permission> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(PermissionMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameExcludingId(String name, Long id) {
        return jpaRepository.existsByNameAndIdNot(name, Math.toIntExact(id));
    }
}
