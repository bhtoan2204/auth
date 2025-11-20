package com.marketplace.auth.domain.aggregate;

import java.util.ArrayList;
import java.util.List;

import com.marketplace.auth.domain.entity.Permission;
import com.marketplace.auth.domain.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class RoleAggregate {

    private Role role;
    @Builder.Default
    private List<Permission> permissions = new ArrayList<>();

    public boolean hasPermission(String permissionName) {
        return permissions.stream()
                .anyMatch(permission -> permission.getName().equals(permissionName));
    }

    public boolean hasAnyPermission(List<String> permissionNames) {
        return permissions.stream()
                .anyMatch(permission -> permissionNames.contains(permission.getName()));
    }

    public boolean hasAllPermissions(List<String> permissionNames) {
        return permissionNames.stream()
                .allMatch(this::hasPermission);
    }

    public void addPermission(Permission permission) {
        if (permission != null && !hasPermission(permission.getName())) {
            permissions.add(permission);
        }
    }

    public void removePermission(Permission permission) {
        if (permission != null) {
            permissions.removeIf(p -> p.getId().equals(permission.getId()));
        }
    }

    public void clearPermissions() {
        permissions.clear();
    }
}
