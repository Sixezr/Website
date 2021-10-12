package semestrovka.module.repositories;

import semestrovka.module.entities.UserModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public final class UserRepositoryJdbcImp implements UserRepository {

    //language=SQL
    private static final String SQL_INSERT_USER = "INSERT INTO \"user\"(name, second_name, phone, email, pass, token, is_admin) VALUES(?,?,?,?,?,?,?)";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "SELECT * FROM  \"user\" WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM  \"user\" WHERE email = ?";

    //language=SQL
    private static final String SQL_FIND_BY_TOKEN = "SELECT * FROM \"user\" WHERE token = ?";

    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM \"user\"";

    //language=SQL
    private static final String SQL_UPDATE_USER = "update \"user\" set name = ?, second_name = ?, phone = ?, pass = ? where id = ?";

    private final DataSource dataSource;

    private final Function<ResultSet, UserModel> accountRowMapper = row -> {
        try {
            int id = row.getInt("id");
            String firstName = row.getString("name");
            String lastName = row.getString("second_name");
            String email = row.getString("email");
            String pass = row.getString("pass");
            String phone = row.getString("phone");
            String token = row.getString("token");
            boolean isAdmin = row.getBoolean("is_admin");

            UserModel user = new UserModel(id, firstName, lastName, email, pass, phone);
            user.setAdmin(isAdmin);
            user.setToken(token);
            return user;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    public UserRepositoryJdbcImp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<UserModel> findAll() {
        List<UserModel> accounts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_FIND_ALL)) {
            ResultSet rows = statement.executeQuery();

            while (rows.next()) {
                accounts.add(accountRowMapper.apply(rows));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return accounts;
    }


    public Optional<UserModel> findById(int id) {
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

    public Optional<UserModel> findByEmail(String email) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_FIND_BY_EMAIL)) {
            statement.setString(1, email);

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
    public Optional<UserModel> findByToken(String token) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_FIND_BY_TOKEN)) {
            statement.setString(1, token);

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

    public void save(UserModel entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_INSERT_USER)) {
            int counter = 1;
            statement.setString(counter++, entity.getName());
            statement.setString(counter++, entity.getSecondName());
            statement.setString(counter++, entity.getPhoneNumber());
            statement.setString(counter++, entity.getEmail());
            statement.setString(counter++, entity.getPass());
            statement.setString(counter++, entity.getToken());
            statement.setBoolean(counter, entity.isAdmin());

            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public void update(UserModel entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_USER)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSecondName());
            statement.setString(3, entity.getPhoneNumber());
            statement.setString(4, entity.getPass());
            statement.setInt(5, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
