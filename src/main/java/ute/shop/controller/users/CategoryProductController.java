package ute.shop.controller.users;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import ute.shop.entity.Category;
import ute.shop.entity.Product;
import ute.shop.service.ICategoryService;
import ute.shop.service.IProductService;
import ute.shop.service.impl.CategoryServiceImpl;
import ute.shop.service.impl.ProductServiceImpl;

@WebServlet("/user/products")
public class CategoryProductController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IProductService productService = new ProductServiceImpl();
	private ICategoryService categoryService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String categoryIdParam = request.getParameter("categoryId");
		List<Product> products;
		String categoryName = "Tất cả sản phẩm"; // mặc định

		if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
			Integer categoryId = Integer.parseInt(categoryIdParam);
			products = productService.getProductsByCategory(categoryId);

			// Lấy tên category từ DB
			Category category = categoryService.findById(categoryId);
			if (category != null) {
				categoryName = category.getName();
			}

			request.setAttribute("selectedCategoryId", categoryId);
		} else {
			products = productService.findAll();
		}

		// Truyền xuống JSP
		request.setAttribute("products", products);
		request.setAttribute("categoryName", categoryName);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/category.jsp");
		dispatcher.forward(request, response);
	}
}
