package semestrovka.module.repositories;

import semestrovka.module.entities.ProductModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ProductRepositoryJdbcImpl implements ProductRepository {

    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM product";

    //language=SQL
    private static final String SQL_FIND_BY_ID  = "SELECT * FROM product where id = ?";

    //language=SQL
    private static final String SQL_FIND_BY_NAME  = "SELECT * FROM product where name = ?";

    //language=SQL
    private static final String SQL_INSERT  = "INSERT INTO product(name, price, image) VALUES(?,?,?)";

    private final DataSource dataSource;

    private final Function<ResultSet, ProductModel> accountRowMapper = row -> {
        try {
            int id = row.getInt("id");
            String name = row.getString("name");
            double price = row.getDouble("price");
            String image = row.getString("image");

            return new ProductModel(id, name, price, image);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    public ProductRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<ProductModel> findByName(String name) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_FIND_BY_NAME)) {
            statement.setString(1, name);

            ResultSet row = statement.executeQuery();
            if (row.next()) {
                return Optional.of(accountRowMapper.apply(row));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<ProductModel> findAll() {
        List<ProductModel> products = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_FIND_ALL)) {
            ResultSet rows = statement.executeQuery();

            while (rows.next()) {
                products.add(accountRowMapper.apply(rows));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return products;
    }

    @Override
    public Optional<ProductModel> findById(Long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);

            ResultSet row = statement.executeQuery();
            if (row.next()) {
                return Optional.of(accountRowMapper.apply(row));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void save(ProductModel entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_INSERT)) {
            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setString(3, entity.getPicture());

            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(ProductModel entity) {

    }
}
