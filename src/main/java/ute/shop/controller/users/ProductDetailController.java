package ute.shop.controller.users;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Product;
import ute.shop.entity.ProductImage;
import ute.shop.entity.ProductVariant;
import ute.shop.entity.Review;
import ute.shop.entity.Shop;
import ute.shop.entity.User;
import ute.shop.service.IProductService;
import ute.shop.service.IProductVariantService;
import ute.shop.service.IOrderService;
import ute.shop.service.IProductImageService;
import ute.shop.service.IReviewService;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.ProductVariantServiceImpl;
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
    private final IProductVariantService variantService = new ProductVariantServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int productId = Integer.parseInt(req.getParameter("id"));

        // Lấy product kèm variants
        Product product = productService.findByIdWithVariants(productId);

     // Lấy ảnh từ bảng ProductImage
     		List<ProductImage> images = productImageService.getImagesByProduct((long) productId);
     		if (images == null) {
     			images = new java.util.ArrayList<>();
     		}

     		// Lấy ảnh từ variant (nếu có)
     		List<ProductVariant> variants = product.getVariants();
     		if (variants != null) {
     			for (ProductVariant v : variants) {
     				if (v.getImageUrl() != null && !v.getImageUrl().isBlank()) {
     					String variantImg = v.getImageUrl().trim();

     					// Tránh trùng ảnh (nếu variant.imageUrl đã có trong ProductImage)
     					boolean exists = images.stream().anyMatch(img -> variantImg.equalsIgnoreCase(img.getImageUrl()));

     					if (!exists) {
     						ProductImage img = new ProductImage();
     						img.setImageUrl(variantImg);
     						img.setMain(false);
     						images.add(img);
     					}
     				}
     			}
     		}

     		// Nếu không có ảnh nào, fallback sang ảnh chính của Product
     		if (images.isEmpty() && product.getImageUrl() != null && !product.getImageUrl().isBlank()) {
     			ProductImage mainImg = new ProductImage();
     			mainImg.setImageUrl(product.getImageUrl());
     			mainImg.setMain(true);
     			images.add(mainImg);
     		}

     		// Đảm bảo có ít nhất một ảnh main
     		boolean hasMain = images.stream().anyMatch(ProductImage::isMain);
     		if (!hasMain && !images.isEmpty()) {
     			images.get(0).setMain(true);
     		}

     		req.setAttribute("images", images);

        // Lấy reviews
        List<Review> reviews = reviewService.getByProductId(productId);

        // Kiểm tra user đã mua chưa
        User account = (User) req.getSession().getAttribute("account");
        boolean hasPurchased = false;
        if (account != null) {
            hasPurchased = orderService.hasPurchased(account.getUserId(), productId);
        }

        // Lấy variant có giá thấp nhất để hiển thị giá
//        List<ProductVariant> variants = product.getVariants();
        ProductVariant minVariant = null;
        if (variants != null && !variants.isEmpty()) {
            minVariant = variants.stream()
                    .min((v1, v2) -> v1.getPrice().compareTo(v2.getPrice()))
                    .orElse(variants.get(0));
            product.setPrice(minVariant.getPrice());
        } else {
            product.setPrice(BigDecimal.ZERO);
        }

        // Lấy map optionName -> list optionValue (giống guest)
        Map<String, List<String>> optionMap = variantService.getOptionMapByProductId(productId);

        // Thông tin shop
        Shop shop = product.getShop();
        int productCount = product.getShop().getProducts().size(); // tránh LazyInitException

        // Set attributes cho JSP
        req.setAttribute("product", product);
//        req.setAttribute("images", images);
        req.setAttribute("reviews", reviews);
        req.setAttribute("hasPurchased", hasPurchased);
        req.setAttribute("minVariant", minVariant);
        req.setAttribute("shop", shop);
        req.setAttribute("productCount", productCount);
        req.setAttribute("optionMap", optionMap); // map optionName -> list optionValue

        // Forward đến JSP user
        req.getRequestDispatcher("/views/user/product-detail.jsp").forward(req, resp);
    }
}
