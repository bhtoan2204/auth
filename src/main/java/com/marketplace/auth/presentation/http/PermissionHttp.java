package com.marketplace.auth.presentation.http;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.auth.application.permission.dto.CreatePermissionCommand;
import com.marketplace.auth.application.permission.dto.PermissionResponse;
import com.marketplace.auth.application.permission.dto.UpdatePermissionCommand;
import com.marketplace.auth.application.permission.service.PermissionApplicationService;
import com.marketplace.auth.presentation.http.request.CreatePermissionRequest;
import com.marketplace.auth.presentation.http.request.UpdatePermissionRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionHttp {

    private final PermissionApplicationService permissionApplicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionResponse create(@Valid @RequestBody CreatePermissionRequest request) {
        return permissionApplicationService.create(new CreatePermissionCommand(request.name()));
    }

    @GetMapping("/{id}")
    public PermissionResponse findById(@PathVariable Long id) {
        return permissionApplicationService.getById(id);
    }

    @GetMapping
    public List<PermissionResponse> list() {
        return permissionApplicationService.list();
    }

    @PutMapping("/{id}")
    public PermissionResponse update(@PathVariable Long id, @Valid @RequestBody UpdatePermissionRequest request) {
        return permissionApplicationService.update(new UpdatePermissionCommand(id, request.name()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        permissionApplicationService.delete(id);
    }
}
