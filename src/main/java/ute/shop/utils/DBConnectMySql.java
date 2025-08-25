package ute.shop.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnectMySql {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = new Properties();
                InputStream input = DBConnectMySql.class.getClassLoader().getResourceAsStream("db.properties");
                props.load(input);

                String driver = props.getProperty("jdbc.driver");
                String url = props.getProperty("jdbc.url");
                String user = props.getProperty("jdbc.username");
                String password = props.getProperty("jdbc.password");

                Class.forName(driver);
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Kết nối DB thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Lỗi kết nối DB");
            }
        }
        return conn;
    }
}
