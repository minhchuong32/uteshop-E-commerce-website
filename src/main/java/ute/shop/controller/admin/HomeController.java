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

    private UserServiceImpl userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String page = req.getParameter("page");
        String view = "/views/admin/dashboard.jsp"; // mặc định
        String activeMenu = "dashboard";

        if (page != null) {
            switch (page) {
                case "dashboard":
                    view = "/views/admin/dashboard.jsp";
                    activeMenu = "dashboard";
                    break;

                case "users":
                    List<User> users = userService.getAllUsers();
                    req.setAttribute("users", users);
                    view = "/views/admin/users/dashboard.jsp";
                    activeMenu = "users";
                    break;

                case "products":
                    view = "/views/admin/products/list.jsp";
                    activeMenu = "products";
                    break;

                case "orders":
                    view = "/views/admin/orders/list.jsp";
                    activeMenu = "orders";
                    break;

                case "stats":
                    view = "/views/admin/stats/index.jsp";
                    activeMenu = "stats";
                    break;

                case "settings":
                    view = "/views/admin/settings.jsp";
                    activeMenu = "settings";
                    break;

                default:
                    view = "/views/admin/dashboard.jsp";
                    activeMenu = "dashboard";
                    break;
            }
        }

        // Gửi biến sang JSP
        req.setAttribute("page", activeMenu);
        req.setAttribute("view", view);

        // Forward đến layout admin
        req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
    }
}
