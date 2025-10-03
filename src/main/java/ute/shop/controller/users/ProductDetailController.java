package ute.shop.controller.users;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Product;
import ute.shop.service.IProductService;
import ute.shop.service.impl.ProductServiceImpl;

@WebServlet(urlPatterns = { "/user/product/detail" })
public class ProductDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int productId = Integer.parseInt(req.getParameter("id"));
        Product product = productService.findById(productId);

        req.setAttribute("product", product);
        req.getRequestDispatcher("/views/user/product-detail.jsp").forward(req, resp);
    }
}
