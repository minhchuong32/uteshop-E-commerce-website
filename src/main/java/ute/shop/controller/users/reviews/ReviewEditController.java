package ute.shop.controller.users.reviews;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Product;
import ute.shop.entity.Review;
import ute.shop.entity.User;
import ute.shop.service.IProductService;
import ute.shop.service.IReviewService;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.ReviewServiceImpl;

@WebServlet("/user/review/edit")
public class ReviewEditController extends HttpServlet {
	private final IReviewService reviewService = new ReviewServiceImpl();
	private final IProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("account");
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
		User user = (User) req.getSession().getAttribute("account");
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
        String mediaUrl = req.getParameter("mediaUrl");

        review.setRating(rating);
        review.setComment(comment);
        review.setMediaUrl(mediaUrl);

        reviewService.updateReview(review);

        req.getSession().setAttribute("success", "Cập nhật đánh giá thành công!");
        String status = URLEncoder.encode("Đã giao", StandardCharsets.UTF_8);
        resp.sendRedirect(req.getContextPath() + "/user/orders?status=" + status);
	}
}
