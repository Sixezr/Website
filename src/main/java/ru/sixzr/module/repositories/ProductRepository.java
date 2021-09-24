package ru.sixzr.module.repositories;

import ru.sixzr.module.entities.ProductModel;

import java.util.Optional;

public interface ProductRepository extends Repository<ProductModel> {
    Optional<ProductModel> findByName(String name);
}
