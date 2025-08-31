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

@WebServlet(urlPatterns = {"/admin/home"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;  

    UserServiceImpl userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String page = req.getParameter("page");
        if (page == null) {
            page = "dashboard"; // mặc định
        }

        // Nếu vào trang quản lý user thì load danh sách user 
        if ("users".equals(page)) {
            List<User> users = userService.getAllUsers();
            req.setAttribute("users", users);
        }

        // Gửi page để home.jsp include trang con 
        req.setAttribute("page", page);
        req.getRequestDispatcher("/WEB-INF/views/admin/home.jsp").forward(req, resp);
    }
}
