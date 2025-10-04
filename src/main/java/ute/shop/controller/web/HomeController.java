package ute.shop.controller.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Product;
import ute.shop.service.ICategoryService;
import ute.shop.service.IProductService;
import ute.shop.service.impl.CategoryServiceImpl;
import ute.shop.service.impl.ProductServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/web/home" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ICategoryService categoryService = new CategoryServiceImpl();
	private final IProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int page = 1;
		int size = 18;

		try {
			page = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}

		String categoryIdStr = req.getParameter("categoryId");
		String minPriceStr = req.getParameter("minPrice");
		String maxPriceStr = req.getParameter("maxPrice");
		String sortBy = req.getParameter("sortBy");

		Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr)
				: null;
		Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
		Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;

		// tổng số sản phẩm
		long totalProducts = productService.countFilterProducts(categoryId, minPrice, maxPrice);
		int totalPages = (int) Math.ceil((double) totalProducts / size);

		List<Product> products = productService.filterProducts(categoryId, minPrice, maxPrice, sortBy, page, size);

		req.setAttribute("categories", categoryService.findAll());
		req.setAttribute("products", products);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages);

		// giữ lại filter khi load lại trang
		req.setAttribute("selectedCategoryId", categoryId);
		req.setAttribute("minPrice", minPrice);
		req.setAttribute("maxPrice", maxPrice);
		req.setAttribute("sortBy", sortBy);

		req.getRequestDispatcher("/views/web/home.jsp").forward(req, resp);
	}
}
