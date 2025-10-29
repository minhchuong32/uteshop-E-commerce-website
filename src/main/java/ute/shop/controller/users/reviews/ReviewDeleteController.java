package ute.shop.controller.users.reviews;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Review;
import ute.shop.entity.User;
import ute.shop.service.IReviewService;
import ute.shop.service.impl.ReviewServiceImpl;

@WebServlet("/user/review/delete")
public class ReviewDeleteController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IReviewService reviewService = new ReviewServiceImpl();
	private String realPath = "D:\\LT WEB\\uteshop-E-commerce-website\\src\\main\\webapp\\assets\\images\\reviews";
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

        if (review != null && review.getUser().getUserId().equals(user.getUserId())) {

            // =================== XÓA FILE ẢNH ===================
            String mediaUrl = review.getMediaUrl();
            if (mediaUrl != null && !mediaUrl.isEmpty()) {
                String fileName = mediaUrl.replace("/images/reviews/", "");

                File fileInProject = new File(realPath, fileName);
                if (fileInProject.exists() && fileInProject.isFile()) {
                    if (fileInProject.delete()) {
                        System.out.println("✅ Đã xóa file: " + fileInProject.getAbsolutePath());
                    } else {
                        System.out.println("⚠ Không thể xóa file: " + fileInProject.getAbsolutePath());
                    }
                } else {
                    System.out.println("⚠ Không tìm thấy file: " + fileInProject.getAbsolutePath());
                }
            }

            // =================== XÓA REVIEW ===================
            reviewService.deleteReview(reviewId);
            req.setAttribute("success", "Xóa đánh giá thành công!");
        } else {
            req.setAttribute("error", "Không thể xóa đánh giá này!");
        }

        String status = URLEncoder.encode("Đã giao", StandardCharsets.UTF_8);
        resp.sendRedirect(req.getContextPath() + "/user/orders?status=" + status);
	}
}
