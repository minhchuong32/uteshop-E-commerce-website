package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import ute.shop.entity.Product;
import ute.shop.entity.Review;
import ute.shop.entity.User;
import ute.shop.service.IProductService;
import ute.shop.service.IReviewService;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.ReviewServiceImpl;

@WebServlet("/review/add")
public class ReviewAddController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IReviewService reviewService = new ReviewServiceImpl();
	private IProductService productService = new ProductServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Integer productId = Integer.valueOf(req.getParameter("productId"));
			Integer rating = Integer.valueOf(req.getParameter("rating"));
			String comment = req.getParameter("comment");

			// Lấy user từ session
			User account = (User) req.getSession().getAttribute("account");
			if (account == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}

			// Lấy product
			Product product = productService.findById(productId);

			// Tạo Review mới
			Review review = new Review();
			review.setProduct(product);
			review.setUser(account);
			review.setRating(rating);
			review.setComment(comment);

			// Lưu
			reviewService.addReview(review);

			// Quay lại trang chi tiết sản phẩm
			resp.sendRedirect(req.getContextPath() + "/user/product/detail?id=" + productId + "#reviews");

		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi khi thêm review");
		}
	}
}
