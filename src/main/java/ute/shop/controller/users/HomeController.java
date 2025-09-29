package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.service.ICategoryService;
import ute.shop.service.IProductService;
import ute.shop.service.impl.CategoryServiceImpl;
import ute.shop.service.impl.ProductServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = { "/user/home" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ICategoryService categoryService = new CategoryServiceImpl();
	private final IProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("categories", categoryService.findAll());
		req.setAttribute("products", productService.findTopProducts(12));
		req.getRequestDispatcher("/views/user/home.jsp").forward(req, resp);
	}
}
