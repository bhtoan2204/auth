package com.marketplace.auth.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketplace.auth.infrastructure.persistence.model.CustomerEntity;

@Repository
public interface CustomerRepository extends BaseRepository<CustomerEntity, Integer> {

    @Query("SELECT c FROM CustomerEntity c WHERE c.userAccount.id = :userId")
    Optional<CustomerEntity> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(c) > 0 FROM CustomerEntity c WHERE c.userAccount.id = :userId")
    boolean existsByUserId(@Param("userId") Integer userId);
}
