package semestrovka.module.collestions;

import java.util.Collection;

public interface ICustomProductCollection<T> extends Collection<T> {
    int getCurrentIndex();

    int getCurrentProductCount();

    double getCurrentPriceForProducts();

    void removeProductById(int id);
}
