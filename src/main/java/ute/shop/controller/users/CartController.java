package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.CartItem;
import ute.shop.entity.User;
import ute.shop.entity.Product;
import ute.shop.service.ICartItemService;
import ute.shop.service.IProductService;
import ute.shop.service.impl.CartItemServiceImpl;
import ute.shop.service.impl.ProductServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/user/cart", "/user/cart/add" })

public class CartController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final ICartItemService cartService = new CartItemServiceImpl();
	private final IProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("account");

		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		List<CartItem> cartItems = cartService.getCartByUser(user);
		req.setAttribute("cartItems", cartItems);

		req.getRequestDispatcher("/views/user/order/cart.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("account");

		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String servletPath = req.getServletPath(); // lấy đường dẫn thực tế

		// ---------------- /user/cart/add ----------------
		if ("/user/cart/add".equals(servletPath)) {
			try {
				int productId = Integer.parseInt(req.getParameter("productId"));
				int quantity = Integer.parseInt(req.getParameter("quantity"));
				Product product = productService.findById(productId);

				if (product != null) {
					cartService.addToCart(user, product, quantity);
					session.setAttribute("cartMessage", "Đã thêm " + product.getName() + " vào giỏ hàng!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Quay lại trang trước
			String referer = req.getHeader("Referer");
			resp.sendRedirect(referer != null ? referer : req.getContextPath() + "/user/home");
			return;
		}

		// ---------------- /user/cart (tăng, giảm, xóa) ----------------
		String action = req.getParameter("action");
		if (action != null) {
			int productId = Integer.parseInt(req.getParameter("productId"));
			Product product = productService.findById(productId);

			switch (action) {
				case "increase":
					cartService.updateQuantity(user, product, 1);
					break;
				case "decrease":
					cartService.updateQuantity(user, product, -1);
					break;
				case "remove":
					int cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
					cartService.removeFromCart(cartItemId);
					break;
			}
		}

		resp.sendRedirect(req.getContextPath() + "/user/cart");
	}

}
