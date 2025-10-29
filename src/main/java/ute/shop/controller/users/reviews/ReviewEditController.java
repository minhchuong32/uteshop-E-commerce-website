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

@WebServlet("/user/review/edit")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024, // 1MB
	    maxFileSize = 1024 * 1024 * 10,  // 10MB
	    maxRequestSize = 1024 * 1024 * 20 // 20MB
	)
public class ReviewEditController extends HttpServlet {
	private final IReviewService reviewService = new ReviewServiceImpl();
	private final IProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int productId = Integer.parseInt(req.getParameter("productId"));
        Product product = productService.findById(productId);
        Review review = reviewService.getByUserAndProduct(user, product);

        req.setAttribute("product", product);
        req.setAttribute("review", review);
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

        int reviewId = Integer.parseInt(req.getParameter("reviewId"));
        Review review = reviewService.getById(reviewId);
        if (review == null || !review.getUser().getUserId().equals(user.getUserId())) {
            String status = URLEncoder.encode("Đã giao", StandardCharsets.UTF_8);
            resp.sendRedirect(req.getContextPath() + "/user/orders?status=" + status);
            return;
        }

        int rating = Integer.parseInt(req.getParameter("rating"));
        String comment = req.getParameter("comment");

        String oldMediaUrl = review.getMediaUrl();
        String newMediaUrl = oldMediaUrl;

        Part filePart = req.getPart("mediaFile");
        if (filePart != null && filePart.getSize() > 0) {
            // Tạo tên file mới
            String originalName = filePart.getSubmittedFileName();
            String fileName = System.currentTimeMillis() + "_" + originalName;

            // ================= Ghi vào thư mục deploy =================
            String deployDir = req.getServletContext().getRealPath("/assets/images/reviews");
            File deployFolder = new File(deployDir);
            if (!deployFolder.exists()) deployFolder.mkdirs();

            File fileInDeploy = new File(deployFolder, fileName);
            filePart.write(fileInDeploy.getAbsolutePath());

            // ================= Copy sang thư mục project =================
            String projectDir = System.getProperty("user.dir") + File.separator +
                    "src" + File.separator + "main" + File.separator +
                    "webapp" + File.separator + "assets" + File.separator +
                    "images" + File.separator + "reviews";
            File projectFolder = new File(projectDir);
            if (!projectFolder.exists()) projectFolder.mkdirs();

            File fileInProject = new File(projectFolder, fileName);
            Files.copy(fileInDeploy.toPath(), fileInProject.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Cập nhật đường dẫn DB (chỉ lưu phần sau /assets)
            newMediaUrl = "/images/reviews/" + fileName;

            // ================= Xóa file cũ (nếu có) =================
            if (oldMediaUrl != null && !oldMediaUrl.isEmpty()) {
                File oldFileInDeploy = new File(req.getServletContext().getRealPath("/assets/" + oldMediaUrl));
                if (oldFileInDeploy.exists()) oldFileInDeploy.delete();

                File oldFileInProject = new File(projectDir, oldMediaUrl.replace("images/reviews/", ""));
                if (oldFileInProject.exists()) oldFileInProject.delete();
            }
        }

        // ================= Cập nhật review =================
        review.setRating(rating);
        review.setComment(comment);
        review.setMediaUrl(newMediaUrl);
        reviewService.updateReview(review);

        req.getSession().setAttribute("success", "Cập nhật đánh giá thành công!");
        String status = URLEncoder.encode("Đã giao", StandardCharsets.UTF_8);
        resp.sendRedirect(req.getContextPath() + "/user/orders?status=" + status);
	}
}
