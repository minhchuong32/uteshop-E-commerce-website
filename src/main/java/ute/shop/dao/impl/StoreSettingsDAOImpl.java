package ute.shop.dao.impl;

import ute.shop.dao.IStoreSettingsDAO;
import ute.shop.models.StoreSettings;
import ute.shop.connection.DBConnectSQLServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StoreSettingsDAOImpl implements IStoreSettingsDAO {

    @Override
    public StoreSettings getSettings() {
        StoreSettings s = null;
        String sql = "SELECT TOP 1 * FROM StoreSettings ORDER BY id";
        try (Connection conn = new DBConnectSQLServer().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                s = new StoreSettings();
                s.setId(rs.getInt("id"));
                s.setStoreName(rs.getString("store_name"));
                s.setEmail(rs.getString("email"));
                s.setHotline(rs.getString("hotline"));
                s.setAddress(rs.getString("address"));
                s.setLogo(rs.getString("logo"));
                s.setTheme(rs.getString("theme"));
                s.setCodEnabled(rs.getBoolean("cod_enabled"));
                s.setMomoEnabled(rs.getBoolean("momo_enabled"));
                s.setVnpayEnabled(rs.getBoolean("vnpay_enabled"));
                s.setCreatedAt(rs.getTimestamp("created_at"));
                s.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public boolean updateSettings(StoreSettings s) {
        String sql = "UPDATE StoreSettings " +
                "SET store_name=?, email=?, hotline=?, address=?, logo=?, theme=?, " +
                "cod_enabled=?, momo_enabled=?, vnpay_enabled=?, updated_at=GETDATE() " +
                "WHERE id=?";
        try (Connection conn = new DBConnectSQLServer().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getStoreName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getHotline());
            ps.setString(4, s.getAddress());
            ps.setString(5, s.getLogo());
            ps.setString(6, s.getTheme());
            ps.setBoolean(7, s.isCodEnabled());
            ps.setBoolean(8, s.isMomoEnabled());
            ps.setBoolean(9, s.isVnpayEnabled());
            ps.setInt(10, s.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }		

    // Test trực tiếp trong main
//    public static void main(String[] args) {
//        StoreSettingsDAOImpl dao = new StoreSettingsDAOImpl();
//
//        // 1. Lấy settings hiện tại
//        StoreSettings settings = dao.getSettings();
//        if (settings != null) {
//            System.out.println("===== STORE SETTINGS BAN ĐẦU =====");
//            System.out.println("Tên cửa hàng: " + settings.getStoreName());
//            System.out.println("Theme: " + settings.getTheme());
//            System.out.println("COD: " + settings.isCodEnabled());
//            System.out.println("Momo: " + settings.isMomoEnabled());
//            System.out.println("VNPay: " + settings.isVnpayEnabled());
//
//            // 2. Thay đổi một số giá trị để test update
//            settings.setTheme("dark-mode");
//            settings.setCodEnabled(!settings.isCodEnabled()); // đảo trạng thái COD
//            settings.setMomoEnabled(true);
//            settings.setVnpayEnabled(false);
//
//            boolean updated = dao.updateSettings(settings);
//            System.out.println(updated ? "✅ Update thành công!" : "❌ Update thất bại!");
//
//            // 3. Lấy lại để kiểm chứng
//            StoreSettings afterUpdate = dao.getSettings();
//            System.out.println("===== STORE SETTINGS SAU UPDATE =====");
//            System.out.println("Tên cửa hàng: " + afterUpdate.getStoreName());
//            System.out.println("Theme: " + afterUpdate.getTheme());
//            System.out.println("COD: " + afterUpdate.isCodEnabled());
//            System.out.println("Momo: " + afterUpdate.isMomoEnabled());
//            System.out.println("VNPay: " + afterUpdate.isVnpayEnabled());
//        } else {
//            System.out.println("⚠️ Không tìm thấy bản ghi StoreSettings trong DB!");
//        }
//    }
}
