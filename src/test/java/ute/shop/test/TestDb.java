//package ute.shop.test;
//
//import java.sql.Connection;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//import java.io.IOException;
//import ute.shop.connection.DBConnectSQLServer;
//
//@WebServlet("/testdb")
//public class TestDb extends HttpServlet {
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html;charset=UTF-8");
//        try {
//        	DBConnectSQLServer db = new DBConnectSQLServer();  // tạo đối tượng
//        	Connection conn = db.getConnection();  	
//            if (conn != null) {
//                resp.getWriter().println("<h3>Kết nối DataBase thành công!</h3>");
//            } else {
//                resp.getWriter().println("<h3>Kết nối DB thất bại!</h3>");
//            }
//        } catch (Exception e) {
//            resp.getWriter().println("<h3>Lỗi: " + e.getMessage() + "</h3>");
//        }
//    }
//}
