package com.marketplace.auth.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketplace.auth.infrastructure.persistence.model.RolePermissionEntity;

@Repository
public interface RolePermissionRepository
        extends BaseRepository<RolePermissionEntity, RolePermissionEntity.RolePermissionId> {

    @Query("SELECT rp FROM RolePermissionEntity rp WHERE rp.role.id = :roleId")
    List<RolePermissionEntity> findByRoleId(@Param("roleId") Integer roleId);

    @Query("SELECT rp FROM RolePermissionEntity rp WHERE rp.permission.id = :permissionId")
    List<RolePermissionEntity> findByPermissionId(@Param("permissionId") Integer permissionId);

    @Query("SELECT rp FROM RolePermissionEntity rp WHERE rp.role.id = :roleId AND rp.permission.id = :permissionId")
    Optional<RolePermissionEntity> findByRoleIdAndPermissionId(@Param("roleId") Integer roleId,
            @Param("permissionId") Integer permissionId);

    @Query("SELECT COUNT(rp) > 0 FROM RolePermissionEntity rp WHERE rp.role.id = :roleId AND rp.permission.id = :permissionId")
    boolean existsByRoleIdAndPermissionId(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    @Query("DELETE FROM RolePermissionEntity rp WHERE rp.role.id = :roleId")
    void deleteByRoleId(@Param("roleId") Integer roleId);
}
