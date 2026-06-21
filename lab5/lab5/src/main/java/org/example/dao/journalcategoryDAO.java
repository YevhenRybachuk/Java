package org.example.dao;

import org.example.model.journalcategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class journalcategoryDAO {
    private Connection conn;

    public journalcategoryDAO(Connection conn) {
        this.conn = conn;
    }

    public void save(journalcategory cat) throws SQLException {
        String sql = "INSERT INTO journalcategory (name, description) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cat.getName());
            ps.setString(2, cat.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while saving category: " + e.getMessage());
            throw e;
        }
    }

    public List<journalcategory> findAll() throws SQLException {
        List<journalcategory> list = new ArrayList<>();
        String sql = "SELECT * FROM journalcategory";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new journalcategory(
                        rs.getInt("category_id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching categories: " + e.getMessage());
            throw e;
        }

        return list;
    }

    public void update(journalcategory cat) throws SQLException {
        String sql = "UPDATE journalcategory SET name=?, description=? WHERE category_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cat.getName());
            ps.setString(2, cat.getDescription());
            ps.setInt(3, cat.getCategoryId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating category: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM journalcategory WHERE category_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting category: " + e.getMessage());
            throw e;
        }
    }
}