package ute.shop.dao.impl;

import ute.shop.connection.DBConnectSQLServer;
import ute.shop.dao.IContactDao;
import ute.shop.models.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContactDaoImpl extends DBConnectSQLServer implements IContactDao {
    public boolean insert(Contact c) {
        String sql = "INSERT INTO Contact (UserID, FullName, Email, Content) VALUES (?,?,?,?)";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (c.getUserId() != null) {
                ps.setInt(1, c.getUserId());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            ps.setString(2, c.getFullName());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getContent());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
