package ute.shop.controller.admin;

import ute.shop.models.User;
import ute.shop.connection.DBConnectSQLServer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/profile/update")
public class UpdateProfileController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Lấy dữ liệu từ form
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");

		if (currentUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password"); // nếu trống thì giữ mật khẩu cũ

		try (Connection conn = new DBConnectSQLServer().getConnection()) {
			String sql;
			if (password == null || password.isEmpty()) {
				sql = "UPDATE users SET username = ?, email = ? WHERE user_id = ?";
			} else {
				// Mã hóa password trước khi lưu
				String hashedPassword = hashPassword(password);
				sql = "UPDATE users SET username = ?, email = ?, password = ? WHERE user_id = ?";
			}

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, username);
				ps.setString(2, email);
				if (password == null || password.isEmpty()) {
					ps.setInt(3, currentUser.getUser_id());
				} else {
					String hashedPassword = hashPassword(password);
					ps.setString(3, hashedPassword);
					ps.setInt(4, currentUser.getUser_id());
				}
				ps.executeUpdate();
			}

			// Cập nhật session
			currentUser.setUsername(username);
			currentUser.setEmail(email);
			if (password != null && !password.isEmpty()) {
				currentUser.setPassword(hashPassword(password));
			}
			session.setAttribute("account", currentUser);

			// Thông báo thành công
			request.setAttribute("message", "Cập nhật thông tin thành công!");
			request.setAttribute("messageType", "success"); // success / error
			request.getRequestDispatcher("/views/admin/profile.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Cập nhật thất bại! Vui lòng thử lại.");
			request.setAttribute("messageType", "error");
			request.getRequestDispatcher("/views/admin/profile.jsp").forward(request, response);
		}
	}

	private String hashPassword(String password) {
		// Thay bằng phương pháp hash phù hợp (ví dụ SHA-256, bcrypt,...)
		// Đây chỉ là ví dụ dùng SHA-256
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
