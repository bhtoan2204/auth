package com.marketplace.auth.application.permission.usecase;

import org.springframework.stereotype.Service;

import com.marketplace.auth.application.permission.dto.PermissionResponse;
import com.marketplace.auth.application.permission.dto.UpdatePermissionCommand;
import com.marketplace.auth.domain.permission.Permission;
import com.marketplace.auth.domain.permission.PermissionRepository;
import com.marketplace.auth.domain.permission.exception.PermissionAlreadyExistsException;
import com.marketplace.auth.domain.permission.exception.PermissionNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePermissionUseCase {
    private final PermissionRepository repository;

    public PermissionResponse handle(UpdatePermissionCommand command) {
        Permission current = repository.findById(command.id())
                .orElseThrow(() -> new PermissionNotFoundException(command.id()));

        if (repository.existsByNameExcludingId(command.name(), command.id())) {
            throw new PermissionAlreadyExistsException(command.name());
        }

        Permission updated = repository.save(current.rename(command.name()));
        return PermissionResponse.from(updated);
    }
}
