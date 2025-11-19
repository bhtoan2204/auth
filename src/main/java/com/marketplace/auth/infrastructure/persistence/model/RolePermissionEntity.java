package com.marketplace.auth.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "role_permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(RolePermissionEntity.RolePermissionId.class)
@ToString(exclude = {"role", "permission"})
@EqualsAndHashCode(callSuper = true, exclude = {"role", "permission"})
public class RolePermissionEntity extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionEntity permission;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class RolePermissionId implements Serializable {
        private Integer role;
        private Integer permission;
    }
}

