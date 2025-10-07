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
import ute.shop.entity.Review;
import ute.shop.entity.Shop;
import ute.shop.entity.User;
import ute.shop.service.IProductService;
import ute.shop.service.IOrderService;
import ute.shop.service.IProductImageService;
import ute.shop.service.IReviewService;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.OrderServiceImpl;
import ute.shop.service.impl.ProductImageServiceImpl;
import ute.shop.service.impl.ReviewServiceImpl;

@WebServlet(urlPatterns = { "/user/product/detail" })
public class ProductDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IProductService productService = new ProductServiceImpl();
    private final IProductImageService productImageService = new ProductImageServiceImpl();
    private final IReviewService reviewService = new ReviewServiceImpl();
    private final IOrderService orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
int productId = Integer.parseInt(req.getParameter("id"));
        
        // Lấy product và variants để tránh LazyInitialization
        Product product = productService.findByIdWithVariants(productId);

        // Lấy list ảnh
        List<ProductImage> images = productImageService.getImagesByProduct((long) productId);

        // Lấy reviews
        List<Review> reviews = reviewService.getByProductId(productId);

        // Kiểm tra user đã mua chưa
        User account = (User) req.getSession().getAttribute("account");
        boolean hasPurchased = false;
        if (account != null) {
            hasPurchased = orderService.hasPurchased(account.getUserId(), productId);
        }

        // Shop info và số lượng sản phẩm của shop (tránh LazyInitialization)
        Shop shop = product.getShop();
        int productCount = productService.countByShop(shop.getShopId());

        req.setAttribute("productCount", productCount);
        req.setAttribute("shop", shop);
        req.setAttribute("hasPurchased", hasPurchased);
        req.setAttribute("product", product);
        req.setAttribute("images", images);
        req.setAttribute("reviews", reviews);

        req.getRequestDispatcher("/views/user/product-detail.jsp").forward(req, resp);
    }
}
