package semestrovka.module.entities;

import java.util.ArrayList;
import java.util.List;

public class CartModel {
    private final List<ProductModel> cart;
    private double price;

    public CartModel() {
        cart = new ArrayList<>();
        price = 0;
    }

    public List<ProductModel> getProducts() {
        return cart;
    }

    public void addProduct(ProductModel productModel) {
        cart.add(productModel);
        price += productModel.getPrice();
    }

    public void removeProduct(ProductModel productModel) {
        cart.remove(productModel);
        price -= productModel.getPrice();
    }

    public void removeProductById(int id) {
        for (ProductModel element : cart) {
            if (element.getId() == id) {
                cart.remove(element);
                price -= element.getPrice();
                return;
            }
        }
    }

    public void clearCart() {
        cart.clear();
    }

    public double getPrice() {
        return price;
    }
}
