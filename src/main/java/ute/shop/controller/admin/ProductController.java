package ute.shop.controller.admin;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ute.shop.entity.Product;
import ute.shop.entity.ProductVariant;
import ute.shop.service.impl.*;
import ute.shop.service.*;

@WebServlet(urlPatterns = { "/admin/products", "/admin/products/add", "/admin/products/edit", "/admin/products/delete",
		"/admin/products/variants" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
		maxFileSize = 1024 * 1024 * 5, // 5MB
		maxRequestSize = 1024 * 1024 * 20 // 20MB
)
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final IProductService productService = new ProductServiceImpl();
	private final IProductVariantService variantService = new ProductVariantServiceImpl();
	private final IProductImageService imageService = new ProductImageServiceImpl();
	private final ICategoryService categoryService = new CategoryServiceImpl();
	private final IShopService shopService = new ShopServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();
		try {
			if (uri.endsWith("/products")) {
				List<Product> allProducts = productService.findAll();
				req.setAttribute("products", allProducts);
				req.setAttribute("page", "products");
				req.setAttribute("view", "/views/admin/products/list.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
			}

			else if (uri.endsWith("/add")) {
				req.setAttribute("categories", categoryService.findAll());
				req.setAttribute("shops", shopService.getAll());
				req.setAttribute("page", "products");
				req.setAttribute("view", "/views/admin/products/add.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
			}

			else if (uri.endsWith("/edit")) {
				int id = Integer.parseInt(req.getParameter("id"));
				Product product = productService.findById(id);
				req.setAttribute("product", product);
				req.setAttribute("variants", variantService.findByProductId(id));
				req.setAttribute("images", imageService.getImagesByProduct((long) id));
				req.setAttribute("categories", categoryService.findAll());
				req.setAttribute("shops", shopService.getAll());
				req.setAttribute("page", "products");
				req.setAttribute("view", "/views/admin/products/edit.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
			}

			else if (uri.endsWith("/variants")) {
				int productId = Integer.parseInt(req.getParameter("productId"));
				List<ProductVariant> variants = variantService.findByProductId(productId);

				// Tạo danh sách DTO để serialize
				List<Map<String, Object>> result = new ArrayList<>();

				for (ProductVariant v : variants) {
					Map<String, Object> item = new HashMap<>();
					item.put("optionName", v.getOptionName());
					item.put("optionValue", v.getOptionValue());
					item.put("price", v.getPrice().toPlainString());
					item.put("oldPrice", v.getOldPrice() != null ? v.getOldPrice().toPlainString() : "");
					item.put("stock", v.getStock());

					// Lấy ảnh của variant, nếu không có thì dùng ảnh mặc định
					String variantImg = v.getImageUrl();
					if (variantImg == null || variantImg.isEmpty()) {
						variantImg = "/images/products/default-product.jpg";
					}
					item.put("imageUrl", variantImg);

					result.add(item);
				}

				// Sử dụng Gson để convert sang JSON
				resp.setContentType("application/json;charset=UTF-8");
				resp.setCharacterEncoding("UTF-8");

				Gson gson = new Gson();
				String json = gson.toJson(result);
				resp.getWriter().write(json);

				return;
			}

			else if (uri.endsWith("/delete")) {
				int id = Integer.parseInt(req.getParameter("id"));

				// Xóa ảnh, biến thể, sản phẩm
				imageService.deleteImage((long) id);
				variantService.deleteByProductId(id);
				productService.delete(id);

				req.getSession().setAttribute("success", "Đã xóa sản phẩm cùng toàn bộ ảnh và biến thể!");
				resp.sendRedirect(req.getContextPath() + "/admin/products");
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Lỗi: " + e.getMessage());
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();
		// Đường dẫn thư mục thật trên server
		String uploadPath = req.getServletContext().getRealPath("/assets/images/products");

		// Tạo 2 thư mục nếu chưa có
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdirs();

		try {
			// =============== ADD PRODUCT ===============
			if (uri.endsWith("/add")) {

				Product product = new Product();
				product.setName(req.getParameter("name"));
				product.setDescription(req.getParameter("description"));
				product.setCategory(categoryService.findById(Integer.parseInt(req.getParameter("categoryId"))));
				product.setShop(shopService.getById(Integer.parseInt(req.getParameter("shopId"))));

				// Ảnh chính
				Part mainFile = req.getPart("imageUrl");
				String mainImage = "default-product.jpg";
				if (mainFile != null && mainFile.getSize() > 0) {
					mainImage = mainFile.getSubmittedFileName();
					mainFile.write(uploadPath + File.separator + mainImage);
				}
				product.setImageUrl("/images/products/" + mainImage);
				productService.save(product);

				// Lưu ảnh chính vào ProductImage
				imageService.addImage(product, "/images/products/" + mainImage, true);

				// Lưu các biến thể
				String[] optionNames = req.getParameterValues("optionName");
				String[] optionValues = req.getParameterValues("optionValue");
				String[] prices = req.getParameterValues("price");
				String[] oldPrices = req.getParameterValues("oldPrice");
				String[] stocks = req.getParameterValues("stock");

				List<Part> variantFiles = req.getParts().stream().filter(p -> "variantImage".equals(p.getName()))
						.toList();

				if (optionNames != null) {
					for (int i = 0; i < optionNames.length; i++) {
						ProductVariant v = new ProductVariant();
						v.setProduct(product);
						v.setOptionName(optionNames[i]);
						v.setOptionValue(optionValues[i]);
						v.setPrice(new BigDecimal(prices[i]));
						v.setOldPrice(oldPrices[i].isEmpty() ? null : new BigDecimal(oldPrices[i]));
						v.setStock(Integer.parseInt(stocks[i]));

						// Lưu ảnh riêng cho từng variant
						String variantImgName = "default-product.jpg";
						if (variantFiles.size() > i && variantFiles.get(i).getSize() > 0) {
							Part variantPart = variantFiles.get(i);

							// Tạo tên file ngẫu nhiên để tránh trùng
							variantImgName = variantPart.getSubmittedFileName();

							// Ghi đúng đường dẫn có dấu "/"
							variantPart.write(uploadPath + File.separator + variantImgName);

							// Ghi vào cột imageUrl trong DB
							v.setImageUrl("/images/products/" + variantImgName);
						} else {
							v.setImageUrl("/images/products/default-product.jpg");
						}

						// Lưu DB ( ảnh phụ )
						variantService.save(v);
						imageService.addImage(product, "/images/products/" + variantImgName, false);
					}

				}

				req.getSession().setAttribute("success", "Thêm sản phẩm, biến thể và ảnh thành công!");
				resp.sendRedirect(req.getContextPath() + "/admin/products");
			}

			// =============== EDIT PRODUCT ===============
			else if (uri.endsWith("/edit")) {
				int id = Integer.parseInt(req.getParameter("id"));
				Product product = productService.findById(id);
				product.setName(req.getParameter("name"));
				product.setDescription(req.getParameter("description"));
				product.setCategory(categoryService.findById(Integer.parseInt(req.getParameter("categoryId"))));
				product.setShop(shopService.getById(Integer.parseInt(req.getParameter("shopId"))));

				// Ảnh chính (update nếu có file mới)
				Part mainFile = req.getPart("imageUrl");
				if (mainFile != null && mainFile.getSize() > 0) {
					String newMain = mainFile.getSubmittedFileName();
					mainFile.write(uploadPath + File.separator + newMain);
					product.setImageUrl("/images/products/" + newMain);
				}
				productService.update(product);

				// Xóa dữ liệu cũ
				variantService.deleteByProductId(id);
				imageService.deleteImage((long) id);

				// Lưu ảnh chính mới
				imageService.addImage(product, product.getImageUrl(), true);

				// Lưu lại các biến thể và ảnh phụ
				String[] optionNames = req.getParameterValues("optionName");
				String[] optionValues = req.getParameterValues("optionValue");
				String[] prices = req.getParameterValues("price");
				String[] oldPrices = req.getParameterValues("oldPrice");
				String[] stocks = req.getParameterValues("stock");

				List<Part> variantFiles = req.getParts().stream().filter(p -> "variantImage".equals(p.getName()))
						.toList();

				if (optionNames != null) {
					for (int i = 0; i < optionNames.length; i++) {
						ProductVariant v = new ProductVariant();
						v.setProduct(product);
						v.setOptionName(optionNames[i]);
						v.setOptionValue(optionValues[i]);
						v.setPrice(new BigDecimal(prices[i]));
						v.setOldPrice(oldPrices[i].isEmpty() ? null : new BigDecimal(oldPrices[i]));
						v.setStock(Integer.parseInt(stocks[i]));
						// Lưu ảnh riêng cho từng variant
						String variantImgName = "default-product.jpg";
						if (variantFiles.size() > i && variantFiles.get(i).getSize() > 0) {
							Part variantPart = variantFiles.get(i);

							// Tạo tên file ngẫu nhiên để tránh trùng
							variantImgName = variantPart.getSubmittedFileName();

							// Ghi đúng đường dẫn có dấu "/"
							variantPart.write(uploadPath + File.separator + variantImgName);

							// Ghi vào cột imageUrl trong DB
							v.setImageUrl("/images/products/" + variantImgName);
						} else {
							v.setImageUrl("/images/products/default-product.jpg");
						}

						// Lưu DB ( ảnh phụ )
						variantService.save(v);
						imageService.addImage(product, "/images/products/" + variantImgName, false);
					}

				}

				req.getSession().setAttribute("success", "Cập nhật sản phẩm, biến thể và ảnh thành công!");
				resp.sendRedirect(req.getContextPath() + "/admin/products");
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.getSession().setAttribute("error", e.getMessage());
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}
}
