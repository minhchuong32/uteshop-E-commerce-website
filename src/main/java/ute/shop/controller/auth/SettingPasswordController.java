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
    private IUserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = (String) request.getSession().getAttribute("email");

        // Check email null
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Phiên đặt lại mật khẩu đã hết hạn hoặc không hợp lệ.");
            request.getRequestDispatcher("/views/auth/setting-password.jsp").forward(request, response);
            return;
        }

        // Check mật khẩu
        if (newPassword == null || confirmPassword == null ||
            newPassword.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
            request.setAttribute("error", "Mật khẩu không được để trống!");
            request.getRequestDispatcher("/views/auth/setting-password.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu nhập lại không khớp!");
            request.getRequestDispatcher("/views/auth/setting-password.jsp").forward(request, response);
            return;
        }

        // Cập nhật DB
        boolean updatePwd = userService.updatePassword(email, newPassword);
        if (updatePwd) {
            request.setAttribute("message", "Đặt lại mật khẩu thành công! Hãy đăng nhập.");
            request.getRequestDispatcher("/views/auth/reset-success.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Có lỗi xảy ra khi đặt lại mật khẩu.");
            request.getRequestDispatcher("/views/auth/setting-password.jsp").forward(request, response);
        }
    }
}
