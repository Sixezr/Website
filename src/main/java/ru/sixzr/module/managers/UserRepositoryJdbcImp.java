package ru.sixzr.module.managers;

import ru.sixzr.module.entities.UserModel;
import ru.sixzr.module.repositories.UserRepository;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserRepositoryJdbcImp implements UserRepository {

    //language=SQL
    private static final String SQL_INSERT_USER = "INSERT INTO \"user\"(name, second_name, phone, email, pass) VALUES(?,?,?,?,?)";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "SELECT * FROM  \"user\" WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM  \"user\" WHERE email = ?";

    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM \"user\"";

    private DataSource dataSource;

    private final Function<ResultSet, UserModel> accountRowMapper = row -> {
        try {
            int id = row.getInt("id");
            String firstName = row.getString("name");
            String lastName = row.getString("second_name");
            String email = row.getString("email");
            String pass = row.getString("pass");
            String phone = row.getString("phone");

            UserModel account = new UserModel(id, firstName, lastName, email, pass, phone);
            return account;
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


    public Optional<UserModel> findById(Long id) {
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

    public void save(UserModel entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_INSERT_USER)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSecondName());
            statement.setString(3, entity.getPhoneNumber());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getPass());

            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public void update(UserModel entity) {

    }
}
