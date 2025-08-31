package ute.shop.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectSQLServer {

    private String serverName;
    private String dbName;
    private String portNumber;
    private String instance;
    private String userID;
    private String password;

    public DBConnectSQLServer() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.err.println("⚠️ Không tìm thấy file db.properties");
                return;
            }
            prop.load(input);

            serverName = prop.getProperty("db.serverName");
            dbName = prop.getProperty("db.dbName");
            portNumber = prop.getProperty("db.portNumber");
            instance = prop.getProperty("db.instance");
            userID = prop.getProperty("db.user");
            password = prop.getProperty("db.password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws ClassNotFoundException {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url;
            if (instance == null || instance.trim().isEmpty()) {
                url = "jdbc:sqlserver://" + serverName + ":" + portNumber
                        + ";databaseName=" + dbName
                        + ";encrypt=true;trustServerCertificate=true";
            } else {
                url = "jdbc:sqlserver://" + serverName + "\\" + instance + ":" + portNumber
                        + ";databaseName=" + dbName
                        + ";encrypt=true;trustServerCertificate=true";
            }

            conn = DriverManager.getConnection(url, userID, password);

            if (conn != null) {
                DatabaseMetaData dm = conn.getMetaData();
                System.out.println("✅ Kết nối SQL Server thành công!");
                System.out.println("Driver: " + dm.getDriverName() + " - " + dm.getDriverVersion());
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi kết nối SQL Server:");
            System.err.println("   ➝ SQLState: " + e.getSQLState());
            System.err.println("   ➝ Error Code: " + e.getErrorCode());
            System.err.println("   ➝ Message: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        try {
            DBConnectSQLServer db = new DBConnectSQLServer();
            Connection conn = db.getConnection();
            if (conn == null) {
                System.out.println("❌ Kết nối thất bại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
