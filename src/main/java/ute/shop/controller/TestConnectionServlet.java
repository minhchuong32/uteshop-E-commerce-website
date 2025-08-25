package ute.shop.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import ute.shop.utils.DBConnectMySql;

@WebServlet("/testdb")
public class TestConnectionServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try {
            Connection conn = DBConnectMySql.getConnection();
            if (conn != null) {
                resp.getWriter().println("<h3>Kết nối DataBase thành công!</h3>");
            } else {
                resp.getWriter().println("<h3>Kết nối DB thất bại!</h3>");
            }
        } catch (Exception e) {
            resp.getWriter().println("<h3>Lỗi: " + e.getMessage() + "</h3>");
        }
    }
}
