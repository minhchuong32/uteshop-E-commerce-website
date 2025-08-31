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

        if (sessionOtp != null && sessionOtp.equals(otp)) {
            request.setAttribute("message", "Xác thực OTP thành công! Vui lòng đặt lại mật khẩu.");
            request.getRequestDispatcher("/WEB-INF/views/auth/setting-password.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Mã OTP không chính xác!");
            request.getRequestDispatcher("/WEB-INF/views/auth/verify-otp.jsp").forward(request, response);
        }
    }
}
