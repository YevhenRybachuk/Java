package org.example.dao;

import org.example.model.annotation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class annotationDAO {
    private Connection conn;

    public annotationDAO(Connection conn) {
        this.conn = conn;
    }

    public void save(annotation a) throws SQLException {
        String sql = "INSERT INTO annotation (language, text, Article_article_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getLanguage());
            ps.setString(2, a.getText());
            ps.setInt(3, a.getArticleId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while saving annotation: " + e.getMessage());
            throw e;
        }
    }

    public List<annotation> findAll() throws SQLException {
        List<annotation> list = new ArrayList<>();
        String sql = "SELECT * FROM annotation";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new annotation(
                        rs.getInt("annotation_id"),
                        rs.getString("language"),
                        rs.getString("text"),
                        rs.getInt("Article_article_id")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching annotations: " + e.getMessage());
            throw e;
        }

        return list;
    }

    public void update(annotation a) throws SQLException {
        String sql = "UPDATE annotation SET language=?, text=?, Article_article_id=? WHERE annotation_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getLanguage());
            ps.setString(2, a.getText());
            ps.setInt(3, a.getArticleId());
            ps.setInt(4, a.getAnnotationId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating annotation: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM annotation WHERE annotation_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting annotation: " + e.getMessage());
            throw e;
        }
    }
}