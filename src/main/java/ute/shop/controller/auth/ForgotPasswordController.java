package ute.shop.controller.auth;

import java.io.IOException;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ute.shop.service.IUserService;
import ute.shop.service.impl.UserServiceImpl;
import ute.shop.utils.SendMail;

@WebServlet(urlPatterns = {"/forgot-password"})
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IUserService userService = new UserServiceImpl();
    SendMail sm = new SendMail();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email_forgot_password");

        boolean userExists = userService.checkExistEmail(email);

        if (userExists) {
            String otp = String.valueOf(new Random().nextInt(900000) + 100000);
            request.getSession().setAttribute("otp", otp);
            request.getSession().setAttribute("email", email);

            // Gửi email OTP
            String subject = "UteShop - Mã OTP xác thực đặt lại mật khẩu";
            String body = "Xin chào,\n\nMã OTP của bạn là: " + otp 
                        + "\nMã có hiệu lực trong 5 phút.\n\nTrân trọng,\nUteShop Team";
            sm.sendMail(email, subject, body);

            request.setAttribute("message", "Mã OTP đã được gửi đến email của bạn!");
            request.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(request, response);
        }	
else {
            request.setAttribute("error", "Không tìm thấy tài khoản với thông tin này!");
            request.getRequestDispatcher("/views/auth/forgot-password.jsp").forward(request, response);
        }
    }
}
