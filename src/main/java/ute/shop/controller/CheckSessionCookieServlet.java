package ute.shop.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/check")
public class CheckSessionCookieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>Kiểm tra Session và Cookie</h2>");

        // ✅ Kiểm tra Session
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            out.println("<p><b>Session:</b> Đã tồn tại</p>");
            out.println("<p>Tài khoản: " + session.getAttribute("account") + "</p>");
        } else {
            out.println("<p><b>Session:</b> Chưa tồn tại</p>");
        }

        // ✅ Kiểm tra Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            out.println("<p><b>Cookies:</b></p><ul>");
            for (Cookie cookie : cookies) {
                out.println("<li>" + cookie.getName() + " = " + cookie.getValue() + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p><b>Cookies:</b> Không có</p>");
        }

        out.println("</body></html>");
    }
}
