package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDbStore {
    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement("SELECT  * FROM users")) {
            try (var it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(createUser(it));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public Optional<User> add(User user) {
        Optional<User> optUser = Optional.empty();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(
                     "INSERT INTO users(name, email, password) VALUES (?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (var id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                    optUser = Optional.of(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return optUser;
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        Optional<User> optUser = Optional.empty();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE email = ? and password = ?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (var it = ps.executeQuery()) {
                if (it.next()) {
                    optUser = Optional.of(createUser(it));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return optUser;
    }

    private User createUser(ResultSet it) throws SQLException {
        return new User(
                it.getInt("id"),
                it.getString("name"),
                it.getString("email"),
                it.getString("password")
        );
    }

    public void clearTable() {
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement("DELETE FROM users")) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
