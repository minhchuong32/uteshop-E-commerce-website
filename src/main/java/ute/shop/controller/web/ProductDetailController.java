package ute.shop.controller.web;

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

@WebServlet(urlPatterns = { "/web/product/detail" })
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
    	Product product = productService.findById(productId);
    	
    	List<Review> reviews = reviewService.getByProductId(productId);

    	// lấy list ảnh từ service
    	List<ProductImage> images = productImageService.getImagesByProduct((long) productId);
    	
    	 // check user có mua chưa
        User account = (User) req.getSession().getAttribute("account");
        boolean hasPurchased = false;
        if (account != null) {
            hasPurchased = orderService.hasPurchased(account.getUserId(), productId);
        }
        
        // Thông tin shop
        Shop shop = product.getShop();
        
     // Đếm số sản phẩm của shop (tránh LazyInitException)
        int productCount = product.getShop().getProducts().size();


        req.setAttribute("productCount", productCount);
        req.setAttribute("shop", shop);
        req.setAttribute("hasPurchased", hasPurchased);
        req.setAttribute("product", product);
        req.setAttribute("images", images);
        req.setAttribute("reviews", reviews);

        req.getRequestDispatcher("/views/web/product-detail.jsp").forward(req, resp);
    }
}
