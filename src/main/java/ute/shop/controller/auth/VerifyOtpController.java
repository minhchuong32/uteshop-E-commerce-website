package ute.shop.controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/forgot-password/verify-otp"})
public class VerifyOtpController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String otp = request.getParameter("otp");
        String sessionOtp = (String) request.getSession().getAttribute("otp");

        // Check OTP nhập vào
        if (otp == null || otp.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập mã OTP!");
            request.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(request, response);
            return;
        }

        // Check session OTP (hết hạn / chưa có)
        if (sessionOtp == null) {
            request.setAttribute("error", "Mã OTP đã hết hạn, vui lòng yêu cầu lại!");
            request.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(request, response);
            return;
        }

        // So sánh OTP
        if (sessionOtp.equals(otp.trim())) {
            // Xác thực thành công → sang trang đặt lại mật khẩu
            request.setAttribute("message", "Xác thực OTP thành công! Vui lòng đặt lại mật khẩu.");
            request.getRequestDispatcher("/views/auth/setting-password.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Mã OTP không chính xác!");
            request.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(request, response);
        }
    }
}
