package ute.shop.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ute.shop.entity.Shop;
import ute.shop.entity.User;
import ute.shop.service.IShopService;
import ute.shop.service.IUserService;
import ute.shop.service.impl.ShopServiceImpl;
import ute.shop.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = { "/admin/shops", "/admin/shops/add", "/admin/shops/edit", "/admin/shops/delete" })
@MultipartConfig // Cho phép upload file
public class ShopController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IShopService shopService = new ShopServiceImpl();
	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();
		try {

			if (uri.endsWith("/shops")) {
				// Hiển thị danh sách cửa hàng
				List<Shop> shops = shopService.getAll();
				req.setAttribute("shops", shops);
				req.setAttribute("page", "shops");
				req.setAttribute("view", "/views/admin/shops/list.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

			} else if (uri.endsWith("/add")) {
				// Load danh sách vendor để hiển thị trong combobox
				List<User> vendors = userService.getUsersByRole("User");
				req.setAttribute("vendors", vendors);
				req.setAttribute("page", "shops");
				req.setAttribute("view", "/views/admin/shops/add.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

			} else if (uri.endsWith("/edit")) {
				String idParam = req.getParameter("id");
				if (idParam != null && !idParam.isEmpty()) {
					int id = Integer.parseInt(idParam);
					Shop shop = shopService.getById(id);
					req.setAttribute("shop", shop);

					// Load vendors để admin có thể thay đổi chủ sở hữu nếu cần
					List<User> vendors = userService.getUsersByRole("Vendor");
					req.setAttribute("vendors", vendors);

					req.setAttribute("page", "shops");
					req.setAttribute("view", "/views/admin/shops/edit.jsp");
					req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
				} else {
					resp.sendRedirect(req.getContextPath() + "/admin/shops");
				}

			} else if (uri.endsWith("/delete")) {
				String idParam = req.getParameter("id");
				if (idParam != null && !idParam.isEmpty()) {
					try {
						int id = Integer.parseInt(idParam);
						shopService.delete(id);

						resp.sendRedirect(req.getContextPath() + "/admin/shops?message=DelSuccess");
					} catch (NumberFormatException e) {

						resp.sendRedirect(req.getContextPath() + "/admin/shops?error=errorDel");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/admin/shops?error=errorGet");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();
		try {

			if (uri.endsWith("/add")) {
				handleAddShop(req, resp);

			} else if (uri.endsWith("/edit")) {
				handleEditShop(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/admin/shops?error=errorPost");
		}
	}

	// ==========================
	// Xử lý thêm cửa hàng
	// ==========================
	private void handleAddShop(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		String name = req.getParameter("name");
		String description = req.getParameter("description");
		int userId = Integer.parseInt(req.getParameter("user_id"));

		// Upload logo nếu có
		Part filePart = req.getPart("logo");
		String logoFileName = null;

		if (filePart != null && filePart.getSize() > 0) {
			logoFileName = extractFileName(filePart);

			String uploadPath = req.getServletContext().getRealPath("/assets/images/shops");

			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			filePart.write(uploadPath + File.separator + logoFileName);
		} else {
			// Nếu không upload, dùng ảnh mặc định
			logoFileName = "default-shop-logo.png";
		}

		User u = new User();
		u.setUserId(userId);

		Shop shop = new Shop();
		shop.setName(name);
		shop.setDescription(description);
		shop.setUser(u);
		shop.setLogo(logoFileName);

		shopService.insert(shop);

		resp.sendRedirect(req.getContextPath() + "/admin/shops?message=AddSuccess");
	}

	// ==========================
	// Xử lý chỉnh sửa cửa hàng
	// ==========================
	private void handleEditShop(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String description = req.getParameter("description");

		Shop shop = shopService.getById(id);
		if (shop == null) {
			req.getSession().setAttribute("error", "Không tìm thấy cửa hàng!");
			resp.sendRedirect(req.getContextPath() + "/admin/shops");
			return;
		}

		// Cập nhật thông tin cơ bản
		shop.setName(name);
		shop.setDescription(description);

		// Kiểm tra nếu có upload logo mới
		Part filePart = req.getPart("logo");
		if (filePart != null && filePart.getSize() > 0) {
			String fileName = extractFileName(filePart);
			String uploadPath = req.getServletContext().getRealPath("/assets/images/shops");

			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			filePart.write(uploadPath + File.separator + fileName);
			shop.setLogo(fileName);
		}

		shopService.update(shop);

		resp.sendRedirect(req.getContextPath() + "/admin/shops?message=EditSuccess");
	}

	// ==========================
	// Hàm tiện ích: Lấy tên file upload
	// ==========================
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return null;
	}
}
