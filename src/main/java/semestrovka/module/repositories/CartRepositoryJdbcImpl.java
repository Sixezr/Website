package semestrovka.module.repositories;

import semestrovka.module.entities.CartModel;
import semestrovka.module.entities.ProductModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CartRepositoryJdbcImpl implements CartRepository {

    //language=SQL
    private static final String SQL_FIND_CART = "select p.id, p.name, p.price, count from cart inner join product p on p.id = cart.product_id where user_id = ?";

    //langyage=SQL
    private static final String SQL_INSERT_OR_UPDATE = "insert into cart(user_id, product_id) values (?,?) on conflict (user_id, product_id) do update set count = cart.count + 1";

    //language=SQL
    private static final String SQL_REMOVE_PRODUCT = "update cart set count = cart.count - 1 where user_id = ? and product_id = ?";

    //language=SQL
    private static final String SQL_DELETE_PRODUCT = "delete from cart where user_id = ? and product_id = ?";

    //language=SQL
    private static final String SQL_DELETE_ALL = "delete from cart where user_id = ?";

    private final DataSource dataSource;

    private final Function<ResultSet, List<ProductModel>> productRowMapper = row -> {
        try {
            List<ProductModel> products = new ArrayList<>();
            int id = row.getInt("id");
            String name = row.getString("name");
            double price = row.getDouble("price");
            int count = row.getInt("count");
            for (int i = 0; i < count ; i++) {
                products.add(new ProductModel(id,name,price));
            }
            return products;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    public CartRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CartModel findCart(int userId) {
        CartModel cart = new CartModel();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_FIND_CART)) {
            statement.setLong(1, userId);
            ResultSet rows = statement.executeQuery();
            List<ProductModel> currentProducts;
            while (rows.next()) {
                currentProducts = productRowMapper.apply(rows);
                for (ProductModel element : currentProducts) {
                    cart.addProduct(element);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return cart;
    }

    @Override
    public void addProduct(int userId, long productId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_INSERT_OR_UPDATE)) {
            statement.setLong(1, userId);
            statement.setLong(2, productId);
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void removeProduct(int userId, int productId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_REMOVE_PRODUCT)) {
            statement.setLong(1, userId);
            statement.setLong(2, productId);
            statement.execute();
        } catch (SQLException e) {
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement statement = conn.prepareStatement(SQL_DELETE_PRODUCT)) {
                statement.setLong(1, userId);
                statement.setLong(2, productId);
                statement.execute();
            } catch (SQLException throwables) {
                throw new IllegalArgumentException(throwables);
            }
        }
    }

    @Override
    public void removeAll(int userId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_DELETE_ALL)) {
            statement.setLong(1, userId);
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
