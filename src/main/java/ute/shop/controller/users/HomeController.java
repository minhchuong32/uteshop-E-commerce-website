package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Product;
import ute.shop.service.ICategoryService;
import ute.shop.service.IProductService;
import ute.shop.service.impl.CategoryServiceImpl;
import ute.shop.service.impl.ProductServiceImpl;

import java.io.IOException;
import java.util.List;
@WebServlet(urlPatterns = { "/user/home" })
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ICategoryService categoryService = new CategoryServiceImpl();
    private final IProductService productService = new ProductServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int size = 9; // số sản phẩm mỗi trang (3 hàng x 3 cột cho grid)

        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        req.setAttribute("categories", categoryService.findAll());

        // Lấy tổng số sản phẩm
        long totalProducts = productService.countAll();
        int totalPages = (int) Math.ceil((double) totalProducts / size);

        // Lấy sản phẩm theo trang
        List<Product> products = productService.findByPage(page, size);

        req.setAttribute("products", products);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/views/user/home.jsp").forward(req, resp);
    }

}
