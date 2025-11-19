package com.marketplace.auth.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "rolePermissions")
@EqualsAndHashCode(callSuper = true, exclude = "rolePermissions")
public class PermissionEntity extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<RolePermissionEntity> rolePermissions = new HashSet<>();
}

