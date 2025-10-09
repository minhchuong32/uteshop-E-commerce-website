package ute.shop.controller.web;

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

@WebServlet("/web/products")
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
            try {
                Integer categoryId = Integer.parseInt(categoryIdParam);
                products = productService.getProductsByCategory(categoryId);

                Category category = categoryService.findById(categoryId);
                if (category != null) {
                    categoryName = category.getName();
                }

                request.setAttribute("selectedCategoryId", categoryId);
            } catch (NumberFormatException e) {
                products = productService.findAll();
            }
        } else {
            products = productService.findAll();
        }

		// Truyền xuống JSP
		request.setAttribute("products", products);
        request.setAttribute("categoryName", categoryName);
        request.setAttribute("categories", categoryService.findAll());

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/web/category.jsp");
		dispatcher.forward(request, response);
	}
}
