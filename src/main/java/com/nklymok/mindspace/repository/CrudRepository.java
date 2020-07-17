package com.nklymok.mindspace.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K> {
    Optional<T> findById(K id);

    List<T> findAll();

    Optional<T> save(T entity);

    Optional<T> update(T entity);

    void delete(T entity);

    void deleteById(K id);
}
