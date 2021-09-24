package ru.sixzr.module.repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    List<T> findAll();
    Optional<T> findById(Long id);
    void save(T entity);
    void update(T entity);
}
