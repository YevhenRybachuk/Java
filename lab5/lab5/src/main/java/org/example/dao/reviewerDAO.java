package org.example.dao;

import org.example.model.reviewer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class reviewerDAO {
    private Connection conn;

    public reviewerDAO(Connection conn) {
        this.conn = conn;
    }

    public void save(reviewer r) throws SQLException {
        String sql = "INSERT INTO reviewer (last_name, first_name, email, scientific_degree) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getLastName());
            ps.setString(2, r.getFirstName());
            ps.setString(3, r.getEmail());
            ps.setString(4, r.getScientificDegree());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while saving reviewer: " + e.getMessage());
            throw e;
        }
    }

    public List<reviewer> findAll() throws SQLException {
        List<reviewer> list = new ArrayList<>();
        String sql = "SELECT * FROM reviewer";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new reviewer(
                        rs.getInt("reviewer_id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("email"),
                        rs.getString("scientific_degree")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching reviewers: " + e.getMessage());
            throw e;
        }

        return list;
    }

    public void update(reviewer r) throws SQLException {
        String sql = "UPDATE reviewer SET last_name=?, first_name=?, email=?, scientific_degree=? WHERE reviewer_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getLastName());
            ps.setString(2, r.getFirstName());
            ps.setString(3, r.getEmail());
            ps.setString(4, r.getScientificDegree());
            ps.setInt(5, r.getReviewerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating reviewer: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM reviewer WHERE reviewer_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting reviewer: " + e.getMessage());
            throw e;
        }
    }
}