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

@WebServlet(urlPatterns = { "/user/cart" })
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

		String action = req.getParameter("action");
		int productId = Integer.parseInt(req.getParameter("productId"));
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		User user = (User) req.getSession().getAttribute("account");

		if (user != null) {
			Product product = productService.findById(productId);

			if ("increase".equals(action)) {
				cartService.updateQuantity(user, product, 1);
			} else if ("decrease".equals(action)) {
				cartService.updateQuantity(user, product, -1);
			} else if ("remove".equals(action)) {
				int cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
				cartService.removeFromCart(cartItemId);
			} else if ("add".equals(action)) {
				// thêm sản phẩm mới hoặc cộng dồn số lượng
				cartService.addToCart(user, product, quantity);
				req.getSession().setAttribute("cartMessage", "Đã thêm " + product.getName() + " vào giỏ hàng!");
				cartService.addToCart(user, product, quantity);
			} else if ("buyNow".equals(action)) {
				// thêm vào giỏ
				cartService.addToCart(user, product, quantity);
				// chuyển tới trang checkout luôn
				resp.sendRedirect(req.getContextPath() + "/user/checkout");
				return;
			}
		}

		resp.sendRedirect(req.getContextPath() + "/user/cart");
	}

}
