package ute.shop.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ute.shop.entity.User;
import ute.shop.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = {
        "/admin/users", 
        "/admin/users/add", 
        "/admin/users/edit", 
        "/admin/users/delete",
        "/admin/users/lock", 
        "/admin/users/unlock"
})
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserServiceImpl userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        if (uri.endsWith("/users")) {
            // Danh sách user
            List<User> users = userService.getAllUsers();
            req.setAttribute("users", users);
            req.setAttribute("page", "users");
            req.setAttribute("view", "/views/admin/users/dashboard.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

        } else if (uri.endsWith("/add")) {
            // Hiển thị form thêm
            req.setAttribute("page", "users");
            req.setAttribute("view", "/views/admin/users/add.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

        } else if (uri.endsWith("/edit")) {
            // Lấy user theo id
            int id = Integer.parseInt(req.getParameter("id"));
            Optional<User> optUser = userService.getUserById(id);

            if (optUser.isPresent()) {
                req.setAttribute("user", optUser.get());
            } else {
                req.setAttribute("error", "Không tìm thấy user!");
            }
            req.setAttribute("page", "users");
            req.setAttribute("view", "/views/admin/users/edit.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

        } else if (uri.endsWith("/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            userService.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/users");

        } else if (uri.endsWith("/lock")) {
            int id = Integer.parseInt(req.getParameter("id"));
            userService.updateStatus(id, "banned");
            resp.sendRedirect(req.getContextPath() + "/admin/users");

        } else if (uri.endsWith("/unlock")) {
            int id = Integer.parseInt(req.getParameter("id"));
            userService.updateStatus(id, "active");
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.endsWith("/add")) {
            // Lấy dữ liệu form
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String role = req.getParameter("role");

            User u = new User();
            u.setUsername(username);
            u.setEmail(email);
            u.setPassword(password); 
            u.setRole(role);
            u.setStatus("active");

            userService.insert(u);

            resp.sendRedirect(req.getContextPath() + "/admin/users");

        } else if (uri.endsWith("/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String role = req.getParameter("role");
            String status = req.getParameter("status");

            Optional<User> optUser = userService.getUserById(id);

            if (optUser.isPresent()) {
                User user = optUser.get();  
                req.setAttribute("user", user);

                user.setUsername(username);
                user.setEmail(email);
                user.setRole(role);
                user.setStatus(status);

                // Cập nhật DB
                userService.update(user);

                // Redirect sau khi update thành công
                resp.sendRedirect(req.getContextPath() + "/admin/users");
            } else {
                req.setAttribute("error", "Không tìm thấy user!");
                req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
            }

        }
    }
}
