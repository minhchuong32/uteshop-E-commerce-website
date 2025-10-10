package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.CartItem;
import ute.shop.entity.User;
import ute.shop.entity.Product;
import ute.shop.entity.ProductVariant;
import ute.shop.service.ICartItemService;
import ute.shop.service.IProductService;
import ute.shop.service.IProductVariantService;
import ute.shop.service.impl.CartItemServiceImpl;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.ProductVariantServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/user/cart", "/user/cart/add", "/user/cart/remove"})
public class CartController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final ICartItemService cartService = new CartItemServiceImpl();
	private final IProductVariantService productVariantService = new ProductVariantServiceImpl();

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

	    String servletPath = req.getServletPath();

	 // 🟢 Thêm sản phẩm vào giỏ
	    if ("/user/cart/add".equals(servletPath)) {
	        try {
	            int variantId = Integer.parseInt(req.getParameter("variantId"));
	            int quantity = Integer.parseInt(req.getParameter("quantity"));

	            ProductVariant variant = productVariantService.findById(variantId);
	            if (variant != null) {
	                cartService.addToCart(user, variant, quantity);
	                session.setAttribute("cartMessage", "✅ Đã thêm vào giỏ: " 
	                        + variant.getProduct().getName() + " - " + variant.getOptionValue());
	                session.setAttribute("cartMessageType", "success");
	            } else {
	                session.setAttribute("cartMessage", "❌ Sản phẩm không tồn tại!");
	                session.setAttribute("cartMessageType", "danger");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            session.setAttribute("cartMessage", "❌ Có lỗi khi thêm vào giỏ hàng!");
	            session.setAttribute("cartMessageType", "danger");
	        }
	        String referer = req.getHeader("Referer");
	        resp.sendRedirect(referer != null ? referer : req.getContextPath() + "/user/home");
	        return;
	    }

	    // 🔴 Xóa sản phẩm khỏi giỏ
	    if ("/user/cart/remove".equals(servletPath)) {
	        try {
	            int cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
	            cartService.removeFromCart(cartItemId);
	            session.setAttribute("cartMessage", "🗑️ Đã xóa sản phẩm khỏi giỏ hàng!");
	            session.setAttribute("cartMessageType", "warning");
	        } catch (Exception e) {
	            e.printStackTrace();
	            session.setAttribute("cartMessage", "❌ Có lỗi khi xóa sản phẩm!");
	            session.setAttribute("cartMessageType", "danger");
	        }
	        resp.sendRedirect(req.getContextPath() + "/user/cart");
	        return;
	    }


//	    // Tăng/Giảm số lượng
//	    String action = req.getParameter("action");
//	    if (action != null) {
//	        int productId = Integer.parseInt(req.getParameter("productId"));
//	        Product product = productService.findById(productId);
//	        switch (action) {
//	            case "increase":
//	                cartService.updateQuantity(user, product, 1);
//	                break;
//	            case "decrease":
//	                cartService.updateQuantity(user, product, -1);
//	                break;
//	        }
//	    }

	    resp.sendRedirect(req.getContextPath() + "/user/cart");
	}


}
