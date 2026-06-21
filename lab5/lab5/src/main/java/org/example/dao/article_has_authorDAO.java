package org.example.dao;

import java.sql.*;

public class article_has_authorDAO {
    private Connection conn;

    public article_has_authorDAO(Connection conn) {
        this.conn = conn;
    }

    public void link(int articleId, int authorId) throws SQLException {
        String sql = "INSERT INTO article_has_author (Article_article_id, Author_author_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, articleId);
            ps.setInt(2, authorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while linking article and author: " + e.getMessage());
            throw e;
        }
    }

    public void unlink(int articleId, int authorId) throws SQLException {
        String sql = "DELETE FROM article_has_author WHERE Article_article_id = ? AND Author_author_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, articleId);
            ps.setInt(2, authorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while unlinking article and author: " + e.getMessage());
            throw e;
        }
    }

    public void printAllLinks() throws SQLException {
        String sql = "SELECT * FROM article_has_author";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("ID Статті | ID Автора");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " <---> " + rs.getInt(2));
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching article-author links: " + e.getMessage());
            throw e;
        }
    }
}