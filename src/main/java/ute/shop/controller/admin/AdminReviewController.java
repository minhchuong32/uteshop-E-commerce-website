package ute.shop.controller.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Review;
import ute.shop.service.IReviewService;
import ute.shop.service.impl.ReviewServiceImpl;

@WebServlet(urlPatterns = { "/admin/reviews/delete", "/admin/reviews/update" })
public class AdminReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final IReviewService reviewService = new ReviewServiceImpl();

	/**
	 * Xử lý các yêu cầu GET, chủ yếu cho việc xóa.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		
		if (uri.endsWith("/delete")) {
			deleteReview(req, resp);
		} else {
			// Chuyển hướng về trang sản phẩm nếu truy cập URL không hợp lệ
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}
	
	/**
	 * Xử lý các yêu cầu POST, chủ yếu cho việc cập nhật.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();

		if (uri.endsWith("/update")) {
			updateReview(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}

	/**
	 * Logic xóa một review.
	 */
	private void deleteReview(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			int reviewId = Integer.parseInt(req.getParameter("id"));
			reviewService.deleteReview(reviewId);
			// Gửi thông báo thành công về view
			resp.sendRedirect(req.getContextPath() + "/admin/products?message=DelReviewSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			// Gửi thông báo lỗi về view
			resp.sendRedirect(req.getContextPath() + "/admin/products?error=ErrorDeletingReview");
		}
	}
	
	/**
	 * Logic cập nhật một review.
	 */
	private void updateReview(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			// Lấy dữ liệu từ form
			int reviewId = Integer.parseInt(req.getParameter("reviewId"));
			int rating = Integer.parseInt(req.getParameter("rating"));
			String comment = req.getParameter("comment");

			// Lấy review hiện tại từ DB
			Review review = reviewService.getById(reviewId);
			if (review != null) {
				// Cập nhật các trường
				review.setRating(rating);
				review.setComment(comment);
				
				// Lưu lại vào DB
				reviewService.updateReview(review);
				
				resp.sendRedirect(req.getContextPath() + "/admin/products?message=EditReviewSuccess");
			} else {
				// Trường hợp không tìm thấy review
				resp.sendRedirect(req.getContextPath() + "/admin/products?error=ReviewNotFound");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/admin/products?error=ErrorUpdatingReview");
		}
	}
}