package com.marketplace.auth.application.permission.usecase;

import org.springframework.stereotype.Service;

import com.marketplace.auth.domain.permission.PermissionRepository;
import com.marketplace.auth.domain.permission.exception.PermissionNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeletePermissionUseCase {
    private final PermissionRepository repository;

    public void handle(Long id) {
        repository.findById(id).orElseThrow(() -> new PermissionNotFoundException(id));
        repository.deleteById(id);
    }
}
