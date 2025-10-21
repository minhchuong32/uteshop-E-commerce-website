package ute.shop.controller.shipper;

import ute.shop.entity.User;
import ute.shop.service.IUserService;
import ute.shop.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/shipper/settings")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
        maxFileSize = 1024 * 1024 * 10,                // 10MB
        maxRequestSize = 1024 * 1024 * 50)             // 50MB
public class SettingController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        
        User shipper = (User) request.getAttribute("account");

        if (shipper == null || !"Shipper".equalsIgnoreCase(shipper.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Forward sang settings.jsp (chỉ thông tin cá nhân)
        request.setAttribute("page", "settings");
        request.setAttribute("view", "/views/shipper/settings.jsp");
        request.getRequestDispatcher("/WEB-INF/decorators/shipper.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

      
        User shipper = (User) request.getAttribute("account");

        if (shipper == null || !"Shipper".equalsIgnoreCase(shipper.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // --- Lấy dữ liệu form ---
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Upload avatar nếu có
        Part avatarPart = request.getPart("avatarFile");
        if (avatarPart != null && avatarPart.getSize() > 0) {
            String fileName = avatarPart.getSubmittedFileName();
            String uploadPath = request.getServletContext().getRealPath("/uploads");
            avatarPart.write(uploadPath + "/" + fileName);
            shipper.setAvatar(fileName);
        }

        // Cập nhật các trường
        if (username != null && !username.trim().isEmpty()) shipper.setUsername(username);
        if (email != null && !email.trim().isEmpty()) shipper.setEmail(email);
        if (phone != null && !phone.trim().isEmpty()) shipper.setPhone(phone);
        if (address != null && !address.trim().isEmpty()) shipper.setAddress(address);

        // --- Đổi mật khẩu ---
        boolean changePwd = false;
        if (oldPassword != null && !oldPassword.isEmpty()) {
            String oldHash = userService.hashPassword(oldPassword);
            if (!oldHash.equals(shipper.getPassword())) {
                request.setAttribute("error", "Mật khẩu hiện tại không đúng!");
                forward(request, response);
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "Xác nhận mật khẩu mới không khớp!");
                forward(request, response);
                return;
            }
            shipper.setPassword(newPassword); // UserServiceImpl sẽ hash lại
            changePwd = true;
        }

        // --- Cập nhật DB ---
        boolean updated = userService.updatePwd(shipper, changePwd);

        if (updated) {
            request.setAttribute("account", shipper);
            request.setAttribute("message", "Cập nhật thông tin thành công!");
            request.setAttribute("messageType", "success");
        } else {
            request.setAttribute("message", "Có lỗi khi cập nhật thông tin!");
            request.setAttribute("messageType", "error");
        }

        forward(request, response);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("page", "settings");
        request.setAttribute("view", "/views/shipper/settings.jsp");
        request.getRequestDispatcher("/WEB-INF/decorators/shipper.jsp").forward(request, response);
    }
}
