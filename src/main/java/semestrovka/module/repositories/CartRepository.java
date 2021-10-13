package semestrovka.module.repositories;

import semestrovka.module.entities.CartModel;

public interface CartRepository {
    CartModel findCart(int userId);

    void addProduct(int userId, long productId);

    void removeProduct(int userId, int productId);

    void removeAll(int userId);
}
