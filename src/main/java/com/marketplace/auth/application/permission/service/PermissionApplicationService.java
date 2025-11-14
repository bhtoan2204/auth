package com.marketplace.auth.application.permission.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marketplace.auth.application.permission.dto.CreatePermissionCommand;
import com.marketplace.auth.application.permission.dto.PermissionResponse;
import com.marketplace.auth.application.permission.dto.UpdatePermissionCommand;
import com.marketplace.auth.application.permission.usecase.CreatePermissionUseCase;
import com.marketplace.auth.application.permission.usecase.DeletePermissionUseCase;
import com.marketplace.auth.application.permission.usecase.GetPermissionUseCase;
import com.marketplace.auth.application.permission.usecase.ListPermissionsUseCase;
import com.marketplace.auth.application.permission.usecase.UpdatePermissionUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionApplicationService {

    private final CreatePermissionUseCase createPermissionUseCase;
    private final UpdatePermissionUseCase updatePermissionUseCase;
    private final DeletePermissionUseCase deletePermissionUseCase;
    private final GetPermissionUseCase getPermissionUseCase;
    private final ListPermissionsUseCase listPermissionsUseCase;

    public PermissionResponse create(CreatePermissionCommand command) {
        return createPermissionUseCase.handle(command);
    }

    public PermissionResponse update(UpdatePermissionCommand command) {
        return updatePermissionUseCase.handle(command);
    }

    public void delete(Long id) {
        deletePermissionUseCase.handle(id);
    }

    public PermissionResponse getById(Long id) {
        return getPermissionUseCase.handle(id);
    }

    public List<PermissionResponse> list() {
        return listPermissionsUseCase.handle();
    }
}
