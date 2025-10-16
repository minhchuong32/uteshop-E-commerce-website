package ute.shop.controller.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Product;
import ute.shop.entity.ProductVariant;
import ute.shop.entity.Shop;
import ute.shop.service.ICategoryService;
import ute.shop.service.IProductService;
import ute.shop.service.IShopService;
import ute.shop.service.impl.CategoryServiceImpl;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.ShopServiceImpl;

@WebServlet("/web/shop/detail")
public class ShopDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IShopService shopService = new ShopServiceImpl();
	private final ICategoryService categoryService = new CategoryServiceImpl();
	private final IProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String idParam = req.getParameter("id");
		int shopId = 0;

		// Kiểm tra hợp lệ
		if (idParam != null && !idParam.isEmpty()) {
			try {
				shopId = Integer.parseInt(idParam);
			} catch (NumberFormatException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID cửa hàng không hợp lệ!");
				return;
			}
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số id trong request!");
			return;
		}

		// Nếu hợp lệ, tiếp tục xử lý
		Shop shop = shopService.getById(shopId);
		if (shop == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy cửa hàng!");
			return;
		}

		List<Product> products;
		req.setAttribute("shop", shop);

		// filter
		int page = 1;
		int size = 6;

		try

		{
			page = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}

		String keyword = req.getParameter("keyword");
		String categoryIdStr = req.getParameter("categoryId");
		String minPriceStr = req.getParameter("minPrice");
		String maxPriceStr = req.getParameter("maxPrice");
		String sortBy = req.getParameter("sortBy");

		Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr)
				: null;
		Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
		Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;

		long totalProducts;

		if (keyword != null && !keyword.isEmpty()) {
			// Tìm kiếm theo keyword trong shop
			totalProducts = productService.countByKeywordAndShop(keyword, shopId);
			products = productService.searchByKeywordAndShop(keyword, shopId, page, size);
			req.setAttribute("keyword", keyword);
		} else {

			// Lọc sản phẩm theo filter trong shop
			totalProducts = productService.countFilterProductsByShop(shopId, categoryId, minPrice, maxPrice);
			products = productService.filterProductsByShop(shopId, categoryId, minPrice, maxPrice, sortBy, page, size);
		}

		// Gán giá hiển thị từ variant rẻ nhất và lưu variant tạm để hiển thị oldPrice
		for (Product p : products) {
			if (p.getVariants() != null && !p.getVariants().isEmpty()) {
				// Chọn variant có giá hiện tại thấp nhất
				ProductVariant minVariant = p.getVariants().stream()
						.min((v1, v2) -> v1.getPrice().compareTo(v2.getPrice())).orElse(p.getVariants().get(0));

				// Set giá hiển thị
				p.setPrice(minVariant.getPrice());

				// Lưu variant vào request để JSP dùng hiển thị oldPrice nếu có
				req.setAttribute("variant_" + p.getProductId(), minVariant);
			} else {
				p.setPrice(BigDecimal.ZERO);
			}
		}

		int totalPages = (int) Math.ceil((double) totalProducts / size);

		req.setAttribute("categories", categoryService.findAll());
		req.setAttribute("products", products);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages);

		req.setAttribute("selectedCategoryId", categoryId);
		req.setAttribute("minPrice", minPrice);
		req.setAttribute("maxPrice", maxPrice);
		req.setAttribute("sortBy", sortBy);

		req.getRequestDispatcher("/views/web/shop-detail.jsp").forward(req, resp);
	}
}