package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.services.CityService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDbStore {
    private final BasicDataSource pool;
    private final CityService cityService;

    public PostDbStore(BasicDataSource pool, CityService cityService) {
        this.pool = pool;
        this.cityService = cityService;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement("SELECT * FROM post")) {
            try (var it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(createPost(it));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post add(Post post) {
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement("INSERT INTO post(name, city_id) VALUES (?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getCity().getId());
            ps.execute();
            try (var id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public boolean update(Post post) {
        boolean result = false;
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement("UPDATE post SET name = ?, city_id = ? WHERE id = ?")) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getCity().getId());
            ps.setInt(3, post.getId());
            result = ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Post findById(int id) {
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")) {
            ps.setInt(1, id);
            try (var it = ps.executeQuery()) {
                if (it.next()) {
                    return createPost(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Post createPost(ResultSet it) throws SQLException {
        return new Post(
                it.getInt("id"),
                it.getString("name"),
                cityService.findById(it.getInt("city_id"))
        );
    }

    public void clearTable() {
        try (var cn = pool.getConnection()) {
            var ps = cn.prepareStatement(
                    "delete from post"
            );
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
