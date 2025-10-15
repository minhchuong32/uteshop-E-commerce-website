package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.CartItem;
import ute.shop.entity.User;
import ute.shop.entity.Product;
import ute.shop.entity.ProductVariant;
import ute.shop.entity.Shop;
import ute.shop.service.ICartItemService;
import ute.shop.service.IProductService;
import ute.shop.service.IProductVariantService;
import ute.shop.service.impl.CartItemServiceImpl;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.ProductVariantServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/user/cart", "/user/cart/add", "/user/cart/remove", "/user/cart/add-now"})
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

	 // ✅ Gom nhóm theo shop
	 Map<Shop, List<CartItem>> cartByShop = cartItems.stream()
	     .collect(Collectors.groupingBy(
	         item -> item.getProductVariant().getProduct().getShop(),
	         LinkedHashMap::new,
	         Collectors.toList()
	     ));

	 // ✅ Tính tổng số lượng toàn giỏ
	 int totalQuantity = cartItems.stream()
	     .mapToInt(CartItem::getQuantity)
	     .sum();

	 req.setAttribute("cartByShop", cartByShop);
	 req.setAttribute("totalQuantity", totalQuantity);
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


	    if ("/user/cart/add-now".equals(servletPath)) {
	        resp.setContentType("application/json;charset=UTF-8");
	        PrintWriter out = resp.getWriter();

	        try {
	            int variantId = Integer.parseInt(req.getParameter("variantId"));
	            int quantity = Integer.parseInt(req.getParameter("quantity"));

	            ProductVariant variant = productVariantService.findById(variantId);
	            if (variant == null) {
	                out.print("{\"success\":false,\"message\":\"Sản phẩm không tồn tại.\"}");
	                return;
	            }

	            // 🟢 Gọi addToCart (trả về boolean)
	            boolean added = cartService.addToCart(user, variant, quantity);
	            if (!added) {
	                out.print("{\"success\":false,\"message\":\"Không thể thêm sản phẩm vào giỏ.\"}");
	                return;
	            }

	            // 🔍 Sau khi thêm, tìm lại cartItem vừa thêm
	            List<CartItem> userCart = cartService.getCartByUser(user);
	            int cartItemId = 0;

	            for (CartItem item : userCart) {
	                if (item.getProductVariant().getId() == variantId) {
	                    cartItemId = item.getCartItemId();
	                    break;
	                }
	            }

	            if (cartItemId == 0) {
	                out.print("{\"success\":true,\"cartItemId\":null}");
	            } else {
	                out.print("{\"success\":true,\"cartItemId\":" + cartItemId + "}");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            out.print("{\"success\":false,\"message\":\"Lỗi server.\"}");
	        } finally {
	            out.flush();
	        }
	        return;
	    }



	    resp.sendRedirect(req.getContextPath() + "/user/cart");
	}


}
