package ute.shop.test;

import ute.shop.utils.DBConnectMySql;
import java.sql.Connection;

public class TestDb {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnectMySql.getConnection();
            if (conn != null) {
                System.out.println("✅ Đã kết nối thành công tới MySQL!");
            } else {
                System.out.println("❌ Kết nối thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
