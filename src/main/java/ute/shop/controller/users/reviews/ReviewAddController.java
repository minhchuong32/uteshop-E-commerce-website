package ute.shop.controller.users.reviews;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ute.shop.entity.Product;
import ute.shop.entity.Review;
import ute.shop.entity.User;
import ute.shop.service.IProductService;
import ute.shop.service.IReviewService;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.ReviewServiceImpl;

@WebServlet("/user/review/add")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024, // 1MB
		maxFileSize = 1024 * 1024 * 10,  // 10MB
		maxRequestSize = 1024 * 1024 * 20 // 20MB
	)
public class ReviewAddController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IReviewService reviewService = new ReviewServiceImpl();
    private final IProductService productService = new ProductServiceImpl();
    private String realProjectPath = "D:\\LT WEB\\uteshop-E-commerce-website\\src\\main\\webapp\\assets\\images\\reviews";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	 User user = (User) req.getAttribute("account");
         if (user == null) {
             resp.sendRedirect(req.getContextPath() + "/login");
             return;
         }

         int productId = Integer.parseInt(req.getParameter("productId"));
         Product product = productService.findById(productId);

         req.setAttribute("product", product);
         req.getRequestDispatcher("/views/user/order/review.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setCharacterEncoding("UTF-8");
        User user = (User) req.getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int productId = Integer.parseInt(req.getParameter("productId"));
        Product product = productService.findById(productId);
        int rating = Integer.parseInt(req.getParameter("rating"));
        String comment = req.getParameter("comment");

        String mediaUrl = null;
        Part filePart = req.getPart("mediaFile");

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();           

            File uploadFolder = new File(realProjectPath);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            File uploadedFile = new File(uploadFolder, fileName);
            filePart.write(uploadedFile.getAbsolutePath());

            // 🔹 Đường dẫn lưu trong DB 
            mediaUrl = "/images/reviews/" + fileName;
        }

        Review review = new Review(null, product, user, rating, comment, mediaUrl);
        reviewService.addReview(review);

        req.setAttribute("success", "Cảm ơn bạn đã đánh giá sản phẩm!");
        String status = URLEncoder.encode("Đã giao", StandardCharsets.UTF_8);
        resp.sendRedirect(req.getContextPath() + "/user/orders?status=" + status);
    }

}
