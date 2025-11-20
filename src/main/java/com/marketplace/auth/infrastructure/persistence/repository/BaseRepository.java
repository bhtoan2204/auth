package com.marketplace.auth.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    default Optional<T> findByIdOptional(ID id) {
        return findById(id);
    }

    default boolean existsByIdOptional(ID id) {
        return existsById(id);
    }
}
