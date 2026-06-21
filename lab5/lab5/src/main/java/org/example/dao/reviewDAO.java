package org.example.dao;

import org.example.model.review;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class reviewDAO {
    private Connection conn;

    public reviewDAO(Connection conn) {
        this.conn = conn;
    }

    public void save(review r) throws SQLException {
        String sql = "INSERT INTO review (review_date, comments, Article_article_id, Reviewer_reviewer_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, r.getReviewDate());
            ps.setString(2, r.getComments());
            ps.setInt(3, r.getArticleId());
            ps.setInt(4, r.getReviewerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while saving review: " + e.getMessage());
            throw e;
        }
    }

    public List<review> findAll() throws SQLException {
        List<review> list = new ArrayList<>();
        String sql = "SELECT * FROM review";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new review(
                        rs.getInt("review_id"),
                        rs.getDate("review_date"),
                        rs.getString("comments"),
                        rs.getInt("Article_article_id"),
                        rs.getInt("Reviewer_reviewer_id")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching reviews: " + e.getMessage());
            throw e;
        }

        return list;
    }

    public void update(review r) throws SQLException {
        String sql = "UPDATE review SET review_date=?, comments=?, Article_article_id=?, Reviewer_reviewer_id=? WHERE review_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, r.getReviewDate());
            ps.setString(2, r.getComments());
            ps.setInt(3, r.getArticleId());
            ps.setInt(4, r.getReviewerId());
            ps.setInt(5, r.getReviewId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating review: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM review WHERE review_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting review: " + e.getMessage());
            throw e;
        }
    }
}