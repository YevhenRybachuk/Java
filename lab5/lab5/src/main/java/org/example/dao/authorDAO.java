package org.example.dao;

import org.example.model.author;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class authorDAO {
    private Connection conn;

    public authorDAO(Connection conn) {
        this.conn = conn;
    }

    public void save(author a) throws SQLException {
        String sql = "INSERT INTO author (last_name, first_name, email, affiliation) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getLastName());
            ps.setString(2, a.getFirstName());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getAffiliation());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while saving author: " + e.getMessage());
            throw e;
        }
    }

    public List<author> findAll() throws SQLException {
        List<author> list = new ArrayList<>();
        String sql = "SELECT * FROM author";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new author(
                        rs.getInt("author_id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("email"),
                        rs.getString("affiliation")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching authors: " + e.getMessage());
            throw e;
        }
        return list;
    }

    public void update(author a) throws SQLException {
        String sql = "UPDATE author SET last_name=?, first_name=?, email=?, affiliation=? WHERE author_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getLastName());
            ps.setString(2, a.getFirstName());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getAffiliation());
            ps.setInt(5, a.getAuthorId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating author: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM author WHERE author_id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting author: " + e.getMessage());
            throw e;
        }
    }

    public List<author> searchByLastName(String lastName) throws SQLException {
        List<author> results = new ArrayList<>();
        String sql = "SELECT * FROM author WHERE LOWER(last_name) LIKE LOWER(?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + lastName + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(new author(
                            rs.getInt("author_id"),
                            rs.getString("last_name"),
                            rs.getString("first_name"),
                            rs.getString("email"),
                            rs.getString("affiliation")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while searching authors: " + e.getMessage());
            throw e;
        }

        return results;
    }
}

