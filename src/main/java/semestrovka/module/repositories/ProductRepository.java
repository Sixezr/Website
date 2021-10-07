package semestrovka.module.repositories;

import semestrovka.module.entities.ProductModel;

import java.util.Optional;

public interface ProductRepository extends Repository<ProductModel> {
    Optional<ProductModel> findByName(String name);
}
