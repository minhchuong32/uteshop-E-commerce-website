package ute.shop.controller.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ute.shop.entity.Shop;
import ute.shop.entity.User;
import ute.shop.service.impl.ShopServiceImpl;

@WebServlet(urlPatterns = { "/admin/shops", "/admin/shops/add", "/admin/shops/edit", "/admin/shops/delete" })
public class ShopController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ShopServiceImpl shopService = new ShopServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();

		if (uri.endsWith("/shops")) {
			// Danh sách shop
			List<Shop> shops = shopService.getAll();
			req.setAttribute("shops", shops);
			req.setAttribute("page", "shops");
			req.setAttribute("view", "/views/admin/shops/list.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

		} else if (uri.endsWith("/add")) {
			// Form thêm shop
			req.setAttribute("page", "shops");
			req.setAttribute("view", "/views/admin/shops/add.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

		} else if (uri.endsWith("/edit")) {
			String idParam = req.getParameter("id");
			if (idParam != null && !idParam.isEmpty()) {
				int id = Integer.parseInt(idParam);
				Shop shop = shopService.getById(id);
				req.setAttribute("shop", shop);
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
				} catch (NumberFormatException e) {
					// log lỗi nếu cần
					System.out.println("Invalid shop id: " + idParam);
				}
			}
			resp.sendRedirect(req.getContextPath() + "/admin/shops");
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();

		if (uri.endsWith("/add")) {
			String name = req.getParameter("name");
			String description = req.getParameter("description");
			int userId = Integer.parseInt(req.getParameter("user_id"));

			Shop shop = new Shop();
			shop.setName(name);
			shop.setDescription(description);

			User u = new User();
			u.setUserId(userId); // chỉ set id thôi
			shop.setUser(u);

			shopService.insert(shop);
			resp.sendRedirect(req.getContextPath() + "/admin/shops");
		} else if (uri.endsWith("/edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			String name = req.getParameter("name");
			String description = req.getParameter("description");

			Shop shop = shopService.getById(id);
			shop.setName(name);
			shop.setDescription(description);

			shopService.update(shop);
			resp.sendRedirect(req.getContextPath() + "/admin/shops");
		}
	}
}
