package com.marketplace.auth.infrastructure.persistence.repository;

import com.marketplace.auth.infrastructure.persistence.model.EmployeeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends BaseRepository<EmployeeEntity, Integer> {

    @Query("SELECT e FROM EmployeeEntity e WHERE e.userAccount.id = :userId")
    Optional<EmployeeEntity> findByUserId(@Param("userId") Integer userId);

    Optional<EmployeeEntity> findByEmployeeCode(String employeeCode);

    @Query("SELECT COUNT(e) > 0 FROM EmployeeEntity e WHERE e.userAccount.id = :userId")
    boolean existsByUserId(@Param("userId") Integer userId);

    boolean existsByEmployeeCode(String employeeCode);

    java.util.List<EmployeeEntity> findByDepartment(String department);
}

