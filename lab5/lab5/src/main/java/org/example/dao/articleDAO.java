package org.example.dao;

import org.example.model.article;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class articleDAO {
    private Connection conn;

    public articleDAO(Connection conn) {
        this.conn = conn;
    }

    public void save(article a) throws SQLException {
        String sql = "INSERT INTO article (title, submission_date, status, JournalCategory_category_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getTitle());
            ps.setDate(2, a.getSubmissionDate());
            ps.setString(3, a.getStatus());
            ps.setInt(4, a.getJournalCategoryId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while saving article: " + e.getMessage());
            throw e;
        }
    }

    public List<article> findAll() throws SQLException {
        List<article> list = new ArrayList<>();
        String sql = "SELECT * FROM article";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new article(
                        rs.getInt("article_id"),
                        rs.getString("title"),
                        rs.getDate("submission_date"),
                        rs.getString("status"),
                        rs.getInt("JournalCategory_category_id")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching articles: " + e.getMessage());
            throw e;
        }

        return list;
    }

    public void update(article a) throws SQLException {
        String sql = "UPDATE article SET title=?, status=?, JournalCategory_category_id=? WHERE article_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getTitle());
            ps.setString(2, a.getStatus());
            ps.setInt(3, a.getJournalCategoryId());
            ps.setInt(4, a.getArticleId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating article: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM article WHERE article_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting article: " + e.getMessage());
            throw e;
        }
    }
}
