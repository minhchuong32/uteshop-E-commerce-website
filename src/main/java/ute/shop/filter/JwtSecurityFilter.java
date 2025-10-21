package ute.shop.filter;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Shop;
import ute.shop.entity.User;
import ute.shop.service.IShopService;
import ute.shop.service.IUserService;
import ute.shop.service.impl.ShopServiceImpl;
import ute.shop.service.impl.UserServiceImpl;
import ute.shop.utils.JwtUtil;

// Đổi tên và giữ nguyên urlPatterns, filter này sẽ làm cả 2 nhiệm vụ
@WebFilter(urlPatterns = { "/admin/*", "/user/*", "/vendor/*", "/shipper/*" })
public class JwtSecurityFilter implements Filter {

	private final IUserService userService = new UserServiceImpl();
	private final IShopService shopService = new ShopServiceImpl();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String token = null;

		// Lấy token từ cookie (Xác thực)
		Cookie[] cookies = httpRequest.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jwt_token".equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}

		// Nếu không có token hoặc token không hợp lệ -> về trang login
		if (token == null || !JwtUtil.validateToken(token)) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?alert=session_expired");
			return;
		}

		try {
			// Token hợp lệ, lấy thông tin User từ DB
			int userId = JwtUtil.extractUserId(token);
			Optional<User> optionalUser = userService.getUserById(userId);

			if (optionalUser.isPresent()) {
				User userAccount = optionalUser.get();
				httpRequest.setAttribute("account", userAccount);

				// ================== LOGIC PHÂN QUYỀN (AUTHORIZATION) ==================
				// Lấy vai trò và URI từ request
				String role = userAccount.getRole();
				String uri = httpRequest.getRequestURI();
				String contextPath = httpRequest.getContextPath();

				// Kiểm tra quyền truy cập dựa trên vai trò
				if (uri.startsWith(contextPath + "/admin") && !"Admin".equalsIgnoreCase(role)) {
					httpResponse.sendRedirect(contextPath + "/access-denied");
					return;
				}

				if (uri.startsWith(contextPath + "/vendor") && !"Vendor".equalsIgnoreCase(role)) {
					httpResponse.sendRedirect(contextPath + "/access-denied");
					return;
				}

				if (uri.startsWith(contextPath + "/shipper") && !"Shipper".equalsIgnoreCase(role)) {
					httpResponse.sendRedirect(contextPath + "/access-denied");
					return;
				}

				// Mặc định, vai trò "User" cũng cần được kiểm tra
				if (uri.startsWith(contextPath + "/user") && !"User".equalsIgnoreCase(role)) {
					httpResponse.sendRedirect(contextPath + "/access-denied");
					return;
				}
			
				// ================== LOGIC LẤY SHOP CHO VENDOR ==================
			   
			    if (uri.startsWith(httpRequest.getContextPath() + "/vendor")) {
			        if ("Vendor".equalsIgnoreCase(userAccount.getRole())) {
			            Shop shop = shopService.findByUserId(userAccount.getUserId());
			            if (shop != null) {
			                // Đặt shop vào request để các controller sau có thể dùng
			                httpRequest.setAttribute("currentShop", shop);
			            } else {
			                // Nếu vendor chưa có shop, chuyển hướng đến trang đăng ký
			                // (tránh lặp vô hạn nếu đang ở chính trang đăng ký)
			                if (!uri.endsWith("/vendor/register-shop")) {
			                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/vendor/register-shop");
			                    return; // Dừng xử lý ngay lập tức
			                }
			            }
			        }
			    }
				// Nếu mọi thứ hợp lệ (xác thực & phân quyền) -> cho phép đi tiếp
				chain.doFilter(request, response);

			} else {
				// Token hợp lệ nhưng không tìm thấy user trong DB
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?alert=user_not_found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?alert=invalid_token");
		}
	}
}