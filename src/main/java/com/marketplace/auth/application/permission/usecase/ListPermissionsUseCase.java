package com.marketplace.auth.application.permission.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marketplace.auth.application.permission.dto.PermissionResponse;
import com.marketplace.auth.domain.permission.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListPermissionsUseCase {
    private final PermissionRepository repository;

    public List<PermissionResponse> handle() {
        return repository.findAll()
                .stream()
                .map(PermissionResponse::from)
                .toList();
    }
}
