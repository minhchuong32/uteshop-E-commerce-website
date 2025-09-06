package ute.shop.controller.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ute.shop.models.User;
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
            User u = userService.getUserById(id);
            req.setAttribute("user", u);
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
            u.setPassword(password); // có thể mã hóa sau
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

            User u = userService.getUserById(id);
            u.setUsername(username);
            u.setEmail(email);
            u.setRole(role);
            u.setStatus(status);

            userService.update(u);

            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }
}
