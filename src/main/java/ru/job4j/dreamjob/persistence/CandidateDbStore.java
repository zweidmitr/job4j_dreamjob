package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDbStore {
    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement("SELECT * FROM candidate")) {
            try (var it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(createCandidate(it));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(
                     "INSERT INTO candidate(name, description, created,photo) VALUES (?,?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (var id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;

    }

    public Candidate findById(int id) {
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")) {
            ps.setInt(1, id);
            try (var it = ps.executeQuery()) {
                if (it.next()) {
                    return createCandidate(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Candidate candidate) {
        boolean result = false;
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(
                     "UPDATE candidate SET name = ?, description = ?, photo = ? WHERE id= ?")) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setBytes(3, candidate.getPhoto());
            ps.setInt(4, candidate.getId());
            result = ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Candidate createCandidate(ResultSet it) throws SQLException {
        var candidate = new Candidate(it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                it.getBytes("photo"),
                it.getTimestamp("created").toLocalDateTime()
        );
        return candidate;
    }

    public void clearTable() {
        try (var cn = pool.getConnection()) {
            var ps = cn.prepareStatement(
                    "delete from candidate"
            );
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
