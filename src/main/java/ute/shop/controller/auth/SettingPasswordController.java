package ute.shop.controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ute.shop.service.IUserService;
import ute.shop.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/forgot-password/setting-password"})
public class SettingPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IUserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = (String) request.getSession().getAttribute("email");

        if (newPassword.equals(confirmPassword)) {
            // cập nhật mật khẩu trong DB
            userService.updatePassword(email, newPassword);

            request.setAttribute("message", "Đặt lại mật khẩu thành công! Hãy đăng nhập.");
            request.getRequestDispatcher("/WEB-INF/views/auth/reset-success.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Mật khẩu không khớp!");
            request.getRequestDispatcher("/WEB-INF/views/auth/setting-password.jsp").forward(request, response);
        }
    }
}
