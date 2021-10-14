package semestrovka.module.entities;

import semestrovka.module.collestions.CustomProductCollections;

import java.util.ArrayList;
import java.util.List;

public class CartModel {
    private final List<ProductModel> cart;
    private final CustomProductCollections products;
    private double price;

    public CartModel() {
        cart = new ArrayList<>();
        products = new CustomProductCollections();
        price = 0;
    }

    public CustomProductCollections getProducts() {
        return products;
    }

    public void addProduct(ProductModel productModel) {
        cart.add(productModel);
        products.add(productModel);
        price += productModel.getPrice();
    }

    public void removeProduct(ProductModel productModel) {
        cart.remove(productModel);
        products.remove(productModel);
        price -= productModel.getPrice();
    }

    public void removeProductById(int id) {
        for (ProductModel element : cart) {
            if (element.getId() == id) {
                cart.remove(element);
                products.removeProductById(id);
                price -= element.getPrice();
                return;
            }
        }
    }

    public void clearCart() {
        price = 0;
        cart.clear();
        products.clear();
    }

    public double getPrice() {
        return price;
    }
}
