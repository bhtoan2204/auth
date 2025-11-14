package com.marketplace.auth.application.permission.usecase;

import org.springframework.stereotype.Service;

import com.marketplace.auth.application.permission.dto.PermissionResponse;
import com.marketplace.auth.domain.permission.PermissionRepository;
import com.marketplace.auth.domain.permission.exception.PermissionNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPermissionUseCase {
    private final PermissionRepository repository;

    public PermissionResponse handle(Long id) {
        return repository.findById(id)
                .map(PermissionResponse::from)
                .orElseThrow(() -> new PermissionNotFoundException(id));
    }
}
