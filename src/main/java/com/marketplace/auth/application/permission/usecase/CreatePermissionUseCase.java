package com.marketplace.auth.application.permission.usecase;

import org.springframework.stereotype.Service;

import com.marketplace.auth.application.permission.dto.CreatePermissionCommand;
import com.marketplace.auth.application.permission.dto.PermissionResponse;
import com.marketplace.auth.domain.permission.Permission;
import com.marketplace.auth.domain.permission.PermissionRepository;
import com.marketplace.auth.domain.permission.exception.PermissionAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatePermissionUseCase {
    private final PermissionRepository repository;

    public PermissionResponse handle(CreatePermissionCommand command) {
        if (repository.existsByName(command.name())) {
            throw new PermissionAlreadyExistsException(command.name());
        }
        Permission saved = repository.save(Permission.create(command.name()));
        return PermissionResponse.from(saved);
    }
}
