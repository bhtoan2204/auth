package com.marketplace.auth.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketplace.auth.infrastructure.persistence.model.UserRoleEntity;

@Repository
public interface UserRoleRepository extends BaseRepository<UserRoleEntity, UserRoleEntity.UserRoleId> {

    @Query("SELECT ur FROM UserRoleEntity ur WHERE ur.userAccount.id = :userId")
    List<UserRoleEntity> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT ur FROM UserRoleEntity ur WHERE ur.role.id = :roleId")
    List<UserRoleEntity> findByRoleId(@Param("roleId") Integer roleId);

    @Query("SELECT ur FROM UserRoleEntity ur WHERE ur.userAccount.id = :userId AND ur.role.id = :roleId")
    Optional<UserRoleEntity> findByUserIdAndRoleId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Query("SELECT COUNT(ur) > 0 FROM UserRoleEntity ur WHERE ur.userAccount.id = :userId AND ur.role.id = :roleId")
    boolean existsByUserIdAndRoleId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Query("DELETE FROM UserRoleEntity ur WHERE ur.userAccount.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);
}
