package ute.shop.controller.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/forgot-password"})
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward đến giao diện forgot-password.jsp
        request.getRequestDispatcher("/WEB-INF/views/auth/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy email hoặc số điện thoại từ form
//        String emailOrPhone = request.getParameter("emailOrPhone");

        // TODO: kiểm tra trong DB xem có tồn tại tài khoản không
        boolean userExists = true; // tạm thời giả sử có

        if (userExists) {
            // Sau này bạn có thể gửi OTP hoặc link reset password qua email
            request.setAttribute("message", "Mã OTP đã được gửi đến email/số điện thoại.");
            request.getRequestDispatcher("/WEB-INF/views/auth/verify-otp.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Không tìm thấy tài khoản với thông tin này!");
            request.getRequestDispatcher("/WEB-INF/views/auth/forgot-password.jsp").forward(request, response);
        }
    }
}
