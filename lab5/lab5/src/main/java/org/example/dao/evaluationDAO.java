package org.example.dao;

import org.example.model.evaluation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class evaluationDAO {
    private Connection conn;

    public evaluationDAO(Connection conn) {
        this.conn = conn;
    }

    public void save(evaluation e) throws SQLException {
        String sql = "INSERT INTO evaluation (score, recommendation, Review_review_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, e.getScore());
            ps.setString(2, e.getRecommendation());
            ps.setInt(3, e.getReviewId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error while saving evaluation: " + ex.getMessage());
            throw ex;
        }
    }

    public List<evaluation> findAll() throws SQLException {
        List<evaluation> list = new ArrayList<>();
        String sql = "SELECT * FROM evaluation";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new evaluation(
                        rs.getInt("evaluation_id"),
                        rs.getInt("score"),
                        rs.getString("recommendation"),
                        rs.getInt("Review_review_id")
                ));
            }

        } catch (SQLException ex) {
            System.out.println("Error while fetching evaluations: " + ex.getMessage());
            throw ex;
        }

        return list;
    }

    public void update(evaluation e) throws SQLException {
        String sql = "UPDATE evaluation SET score=?, recommendation=?, Review_review_id=? WHERE evaluation_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, e.getScore());
            ps.setString(2, e.getRecommendation());
            ps.setInt(3, e.getReviewId());
            ps.setInt(4, e.getEvaluationId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error while updating evaluation: " + ex.getMessage());
            throw ex;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM evaluation WHERE evaluation_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error while deleting evaluation: " + ex.getMessage());
            throw ex;
        }
    }
}