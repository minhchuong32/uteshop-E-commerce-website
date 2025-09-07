package ute.shop.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ute.shop.dao.IShopDao;
import ute.shop.models.Shop;
import ute.shop.connection.DBConnectSQLServer; 

public class ShopDaoImpl extends DBConnectSQLServer implements IShopDao {

    @Override
    public List<Shop> getAll() {
        List<Shop> list = new ArrayList<>();
        String sql = "SELECT shop_id, user_id, name, description, created_at FROM shops ORDER BY shop_id";

        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Shop s = new Shop();
                s.setShopId(rs.getInt("shop_id"));
                s.setUserId(rs.getInt("user_id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                s.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(s);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Shop getById(int id) {
        String sql = "SELECT shop_id, user_id, name, description, created_at FROM shops WHERE shop_id=?";
        Shop s = null;
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = new Shop();
                    s.setShopId(rs.getInt("shop_id"));
                    s.setUserId(rs.getInt("user_id"));
                    s.setName(rs.getString("name"));
                    s.setDescription(rs.getString("description"));
                    s.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public void insert(Shop shop) {
        String sql = "INSERT INTO shops(user_id, name, description, created_at) VALUES(?,?,?,?)";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, shop.getUserId());
            ps.setString(2, shop.getName());
            ps.setString(3, shop.getDescription());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Shop shop) {
        String sql = "UPDATE shops SET name=?, description=? WHERE shop_id=?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, shop.getName());
            ps.setString(2, shop.getDescription());
            ps.setInt(3, shop.getShopId());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM shops WHERE shop_id=?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
