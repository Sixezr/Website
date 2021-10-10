package semestrovka.module.entities;

import java.util.Objects;

public class ProductModel {
    private int id;
    private String name;
    private double price;
    private String picture;

    public ProductModel(String name, double price, String picture) {
        this.name = name;
        this.price = price;
        this.picture = picture;
    }

    public ProductModel(int id, String name, double price) {
        this(name, price, null);
        this.id = id;
    }

    public ProductModel(int id, String name, double price, String picture) {
        this(name, price, picture);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return id == that.id && Double.compare(that.price, price) == 0 && Objects.equals(name, that.name) && Objects.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, picture);
    }
}
