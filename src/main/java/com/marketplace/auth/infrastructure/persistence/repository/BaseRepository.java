package com.marketplace.auth.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    default Optional<T> findByIdOptional(ID id) {
        return findById(id);
    }

    default boolean existsByIdOptional(ID id) {
        return existsById(id);
    }
}

