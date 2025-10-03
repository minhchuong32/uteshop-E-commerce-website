package ute.shop.controller.users;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Product;
import ute.shop.entity.ProductImage;
import ute.shop.service.IProductService;
import ute.shop.service.IProductImageService;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.ProductImageServiceImpl;

@WebServlet(urlPatterns = { "/user/product/detail" })
public class ProductDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IProductService productService = new ProductServiceImpl();
    private final IProductImageService productImageService = new ProductImageServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int productId = Integer.parseInt(req.getParameter("id"));
        Product product = productService.findById(productId);

        // lấy list ảnh từ service
        List<ProductImage> images = productImageService.getImagesByProduct((long) productId);

        req.setAttribute("product", product);
        req.setAttribute("images", images);

        req.getRequestDispatcher("/views/user/product-detail.jsp").forward(req, resp);
    }
}
