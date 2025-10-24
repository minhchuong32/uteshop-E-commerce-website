package ute.shop.controller.users.reviews;

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
	private final IReviewService reviewService = new ReviewServiceImpl();
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
            reviewService.deleteReview(reviewId);
            req.getSession().setAttribute("success", "Xóa đánh giá thành công!");
        } else {
            req.getSession().setAttribute("error", "Không thể xóa đánh giá này!");
        }

        String status = URLEncoder.encode("Đã giao", StandardCharsets.UTF_8);
        resp.sendRedirect(req.getContextPath() + "/user/orders?status=" + status);
	}
}
